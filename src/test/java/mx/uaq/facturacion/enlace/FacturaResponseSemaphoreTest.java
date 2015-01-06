package mx.uaq.facturacion.enlace;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;

public class FacturaResponseSemaphoreTest {

	private FacturaResponseSemaphore facturaResponseSemaphore;

	@Before
	public void setup() {
		facturaResponseSemaphore = new FacturaResponseSemaphore(1, true);
	}

	@Test
	public void shouldBeJavaSemaphore() {
		assertThat(facturaResponseSemaphore, instanceOf(Semaphore.class));
	}

	@Test
	public void shouldHoldTimbradoXmlAndPdfStream() throws Exception {
		InputStream facturaXml = new ByteArrayInputStream("".getBytes());
		facturaResponseSemaphore.setFacturaXml(facturaXml);
		InputStream facturaPdf = new ByteArrayInputStream("pdf".getBytes());
		facturaResponseSemaphore.setFacturaPdf(facturaPdf);
		assertThat(facturaXml,
				equalTo(facturaResponseSemaphore.getFacturaXml()));
		assertThat(facturaPdf,
				equalTo(facturaResponseSemaphore.getFacturaPdf()));
	}

	@Test
	public void shouldHaveAQueueSemaphoreHolder() throws Exception {
		assertThat(FacturaResponseSemaphore.semaphoreQueue,
				instanceOf(ConcurrentHashMap.class));
	}

	@Test
	public void putFacturaSemaphoresOnQueue() throws Exception {
		FacturaResponseSemaphore.semaphoreQueue.put("key",
				new FacturaResponseSemaphore(10, true));
		assertThat(FacturaResponseSemaphore.semaphoreQueue.get("key"),
				notNullValue());
	}
}
