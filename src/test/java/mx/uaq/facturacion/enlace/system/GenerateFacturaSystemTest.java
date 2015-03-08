package mx.uaq.facturacion.enlace.system;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.uaq.facturacion.enlace.FacturacionGateway;
import mx.uaq.fuaq.business.api.TimbradoRequest;
import mx.uaq.fuaq.business.api.TimbradoResponse;
import mx.uaq.fuaq.portal.business.facturacion.ComprobanteFacturacionLayout;
import mx.uaq.fuaq.portal.business.facturacion.ConceptoFacturacionLayout;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supercsv.ext.io.CsvAnnotationBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Ignore("System Test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:facturacion-integration.xml",
		"classpath:facturas-zip.processing.xml", "classpath:ftp-channel.xml",
		"classpath:email-facturas.poller.xml" })
@TestPropertySource(properties = { "host = 118.210.11.110",
		"availableServerPort=21", "userid=prueba", "password=prueba",
		"remoteDirectory:layout" })
public class GenerateFacturaSystemTest {
	private Writer stringWriter;
	private CsvAnnotationBeanWriter<ComprobanteFacturacionLayout> beanWriter;
	private CsvAnnotationBeanWriter<ConceptoFacturacionLayout> conceptoBeanWriter;
	private BigDecimal total = BigDecimal.valueOf(
			Double.valueOf(new SimpleDateFormat("Hmm.SSS").format(new Date())))
			.setScale(2, BigDecimal.ROUND_CEILING);

	@Before
	public void setUp() throws Exception {
		stringWriter = new StringWriter(1024);
		beanWriter = new CsvAnnotationBeanWriter<ComprobanteFacturacionLayout>(
				ComprobanteFacturacionLayout.class, stringWriter,
				new CsvPreference.Builder('"', 124, "|\n").build());
		conceptoBeanWriter = new CsvAnnotationBeanWriter<ConceptoFacturacionLayout>(
				ConceptoFacturacionLayout.class, stringWriter,
				new CsvPreference.Builder('"', 124, "|\n").build());
	}

	@Autowired
	FacturacionGateway facturacionGateway;

	@Test
	public void test() throws Exception {
		TimbradoRequest facturacionRequest = new TimbradoRequest();
		Date fechaGeneracion = new Date();
		beanWriter.write(createBasicComprobanteTimbradoLayout(fechaGeneracion));
		beanWriter.flush();
		conceptoBeanWriter.write(createBasicConceptoFacturacionLayout());
		conceptoBeanWriter.flush();
		facturacionRequest.setIdFactura(new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss").format(fechaGeneracion));
		facturacionRequest.setPayload(stringWriter.toString());
		TimbradoResponse timbradoResponse = facturacionGateway
				.generateBill(facturacionRequest);
		assertThat(timbradoResponse.getFacturaXml(), notNullValue());
		System.err.println("XML >>"
				+ IOUtils.toString(timbradoResponse.getFacturaXml()));
		System.out.println(stringWriter);
		System.out
				.println(String.format("UAQ_AAA010101AAA_%s.txt",
						new SimpleDateFormat("yyyyMMddHHmmss")
								.format(fechaGeneracion)));
	}

	private ComprobanteFacturacionLayout createBasicComprobanteTimbradoLayout(
			final Date fechaGeneracion) {
		final BigDecimal subtotal = total;
		ComprobanteFacturacionLayout comprobanteLayout = new ComprobanteFacturacionLayout();
		comprobanteLayout.setRfcEmisor("AAA010101AAA");
		comprobanteLayout.setFacturaId(Long.valueOf(new SimpleDateFormat(
				"Hmmss").format(fechaGeneracion)));
		comprobanteLayout.setFechaGeneracion(fechaGeneracion);
		comprobanteLayout.setSubtotal(subtotal);
		comprobanteLayout.setTipoMonedaId(2);
		comprobanteLayout.setTotal(total);
		comprobanteLayout.setTipoComprobante("I");
		comprobanteLayout.setMetodoPagoId(6);
		comprobanteLayout.setBancoId(12);
		comprobanteLayout.setNombreSucursal("MATRIZ");
		comprobanteLayout.setAliasCliente("GEI960731EL6");
		comprobanteLayout.setRfcReceptor("GEI960731EL6");
		comprobanteLayout.setNombreReceptor("Protección Civil");
		comprobanteLayout.setCalleReceptor("Avenida Playa Roqueta");
		comprobanteLayout.setNumeroExteriorReceptor("34-C");
		comprobanteLayout.setColoniaReceptor("Sta. María Magdalena");
		comprobanteLayout.setMunicipioReceptor("Azoyú");
		comprobanteLayout.setEstadoReceptor(12);
		comprobanteLayout.setPaisReceptor(1);
		comprobanteLayout.setCodigoPostalReceptor(39380);
		comprobanteLayout.setNombreContactoReceptor("Protección Civil");
		comprobanteLayout.setEmailContactoReceptor("j.gabriel.paz.s@gmail.com");
		comprobanteLayout.setEnvioEmailFactura(Boolean.TRUE);
		comprobanteLayout.setTotalImpuestosTrasladados(BigDecimal.ZERO
				.setScale(2));
		return comprobanteLayout;
	}

	private ConceptoFacturacionLayout createBasicConceptoFacturacionLayout() {
		ConceptoFacturacionLayout conceptoLayout = new ConceptoFacturacionLayout();
		conceptoLayout.setCantidad(1L);
		conceptoLayout.setUnidad("N/A");
		conceptoLayout.setClaveDescripcion("F01");
		conceptoLayout.setObservacionDetalle(String.format("%s %s %s",
				RandomStringUtils.randomAlphabetic(23),
				RandomStringUtils.randomAlphabetic(23),
				RandomStringUtils.randomAlphabetic(23)));
		conceptoLayout.setValorUnitario(total);
		conceptoLayout.setImpuestoPorcentaje(0);
		return conceptoLayout;
	}
}
