package mx.uaq.facturacion.enlace;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import mx.fuaq.test.util.ftp.TestUserManager;
import mx.uaq.fuaq.business.api.TimbradoRequest;
import mx.uaq.fuaq.business.api.TimbradoResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:facturacion-integration.xml",
		"classpath:facturas-zip.processing.xml" })
@TestPropertySource(properties = { "host = localhost",
		"availableServerPort = 4242", "userid = demo", "password=demo" })
public class FacturacionGatewayTest {

	private static FtpServer server;
	private static String xmlContents = "<cfdi:Comprobante xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"3.2\" serie=\"FE\" "
			+ "folio=\"13\" fecha=\"2014-12-12T09:33:36\" "
			+ "formaDePago=\"Pago en una sola exhibicion\" noCertificado=\"20001000000100005867\" "
			+ "condicionesDePago=\"Contado\" subTotal=\"200.00\" TipoCambio=\"1.0000\" Moneda=\"Pesos Méxicanos\" "
			+ "total=\"200.00\" metodoDePago=\"Tarjeta de débito\" tipoDeComprobante=\"ingreso\" "
			+ "LugarExpedicion=\"Hidalgo, Acatlán\" NumCtaPago=\"0000\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\"/>";
	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();
	private String keyLayoutFactura;
	private String xmlContentsExpected;
	@Autowired
	FacturacionGateway facturacionGateway;
	private File ftpFolder;
	@Autowired
	@Qualifier("uploadFacturaFtpChannel")
	DirectChannel uploadLayoutFileFactura;
	@Autowired
	@Qualifier("facturaIdChannel")
	MessageChannel facturaIdChannel;
	private String pdfContentsExpected = "it is a pdf" + new Date();
	private File filepayload;
	private File pdfpayload;

	@Before
	public void setup() throws IOException, FtpException {
		ftpFolder = temporaryFolder.newFolder("server");
		TestUserManager userManager = new TestUserManager(temporaryFolder
				.getRoot().getAbsolutePath());
		FtpServerFactory serverFactory = new FtpServerFactory();
		serverFactory.setUserManager(userManager);
		ListenerFactory factory = new ListenerFactory();

		factory.setPort(4242);
		serverFactory.addListener("default", factory.createListener());
		server = serverFactory.createServer();
		server.start();
		ChannelInterceptor adapChannelInterceptor = new ChannelInterceptorAdapter() {
			@Override
			public void postSend(Message<?> message, MessageChannel channel,
					boolean sent) {
				super.postSend(message, channel, sent);
				new Thread() {
					public void run() {
						Message<?> xmlMessage = MessageBuilder
								.withPayload(filepayload)
								.setHeader("facturaPdfFile", pdfpayload)
								.build();
						facturaIdChannel.send(xmlMessage);
					};
				}.start();
			}
		};
		uploadLayoutFileFactura.addInterceptor(adapChannelInterceptor);
		keyLayoutFactura = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.format(new Date());
		try {
			filepayload = temporaryFolder.newFile("example1.xml");
			pdfpayload = new File("example1.pdf");
			xmlContentsExpected = xmlContents.replace(
					"fecha=\"2014-12-12T09:33:36\"", "fecha=\""
							+ keyLayoutFactura + "\"");
			FileUtils.write(filepayload, xmlContentsExpected);
			FileUtils.write(pdfpayload, pdfContentsExpected);
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create factura payload", e);
		}
	}

	@Test
	public void test() throws IOException {
		TimbradoRequest timbradoRequest = new TimbradoRequest();
		String payload = UUID.randomUUID().toString();
		timbradoRequest.setPayload(payload);
		timbradoRequest.setIdFactura(keyLayoutFactura);
		TimbradoResponse timbradoResponse = facturacionGateway
				.generateBill(timbradoRequest);
		File uploadedFile = new File(this.ftpFolder, "textFile.txt");
		assertTrue(uploadedFile.exists());
		assertThat(payload,
				equalTo(IOUtils.toString(new FileInputStream(uploadedFile))));
		Object facturaSemaphore = FacturaResponseSemaphore.semaphoreQueue
				.get(keyLayoutFactura);
		assertThat(facturaSemaphore, notNullValue());
		assertThat(timbradoResponse.getFacturaXml(), notNullValue());
		assertThat(IOUtils.toString(timbradoResponse.getFacturaXml()),
				equalTo(xmlContentsExpected));
		assertThat(timbradoResponse.getFacturaPdf(), notNullValue());
		assertThat(IOUtils.toString(timbradoResponse.getFacturaPdf()),
				equalTo(pdfContentsExpected));
	}

	@After
	public void shutDown() {
		server.stop();
	}

}
