package mx.uaq.facturacion.enlace;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.zeroturnaround.zip.ZipUtil;

public class ZipAttachmentsDecompressor {

	private static String facturasDirectory = "target/out/zip/";

	public Message<?> decompressFiles(Message<?> messageZip)
			throws FileNotFoundException {
		final FacturaZipFileContents zipContents = new FacturaZipFileContents();
		System.out.println(messageZip.getPayload().getClass());

		ZipUtil.unpack(
				new ByteArrayInputStream((byte[]) messageZip.getPayload()),
				new File(facturasDirectory), (name) -> {
					if (name.endsWith(".xml"))
						zipContents.xmlFilename = name;
					else
						zipContents.pdfFilename = name;
					return name;
				});
		System.out.println("Facturas: " + zipContents);
		Message<?> xmlMessage = MessageBuilder
				.withPayload(
						new File(facturasDirectory, zipContents.xmlFilename))
				.setHeader("facturaPdfFile",
						new File(facturasDirectory, zipContents.pdfFilename))
				.build();
		return xmlMessage;
	}

	private static class FacturaZipFileContents {
		String xmlFilename;
		String pdfFilename;

		@Override
		public String toString() {
			return String.format("[%s,%s]", xmlFilename, pdfFilename);
		}
	}

	public void allocateBill(Message<?> factura) throws InterruptedException {
		System.out.println("Message: " + factura);
		FacturaResponseSemaphore notifier = FacturaResponseSemaphore.semaphoreQueue
				.get(factura.getHeaders().get("facturaId"));
		System.out.println("Doing task...");
		Thread.sleep(500);
		try {
			notifier.setFacturaXml(new FileInputStream((File) factura
					.getPayload()));
			notifier.setFacturaPdf(new FileInputStream((File) factura
					.getHeaders().get("facturaPdfFile")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		notifier.release();
	}
}
