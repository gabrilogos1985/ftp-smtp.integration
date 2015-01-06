package mx.uaq.facturacion.enlace;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import mx.uaq.fuaq.business.api.TimbradoResponse;
import mx.uaq.fuaq.business.api.TimbradoRequest;

public class FacturacionGateway {

	@Autowired
	@Qualifier("uploadFacturaFtpChannel")
	MessageChannel uploadLayoutFileFactura;

	public TimbradoResponse generateBill(
			final TimbradoRequest facturacionRequest) {
		Message<String> ftpFacturaUploadMessage = MessageBuilder
				.withPayload(facturacionRequest.getPayload())
				.setHeader("uploadFacturaFilename",
						createFilename(facturacionRequest.getIdFactura()))
				.build();
		FacturaResponseSemaphore facturaSemaphore = new FacturaResponseSemaphore(
				1, true);
		try {
			facturaSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FacturaResponseSemaphore.semaphoreQueue.put(
				facturacionRequest.getIdFactura(), facturaSemaphore);
		System.out.println("E N V I A D O >>>>>> "
				+ uploadLayoutFileFactura.send(ftpFacturaUploadMessage));
		try {
			facturaSemaphore.tryAcquire(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimbradoResponse timbradoResponse = new TimbradoResponse();
		timbradoResponse.setFacturaXml(facturaSemaphore.getFacturaXml());
		timbradoResponse.setFacturaPdf(facturaSemaphore.getFacturaPdf());
		return timbradoResponse;
	}

	String createFilename(String keyFactura) {
		return String.format("UAQ_AAA010101AAA_%s.txt",
				keyFactura.replaceAll("[:T-]", ""));
	}
}
