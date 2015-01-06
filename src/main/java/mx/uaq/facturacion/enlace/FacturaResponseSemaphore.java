package mx.uaq.facturacion.enlace;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class FacturaResponseSemaphore extends Semaphore {

	private static final long serialVersionUID = 1L;
	public static ConcurrentHashMap<String, FacturaResponseSemaphore> semaphoreQueue = new ConcurrentHashMap<String, FacturaResponseSemaphore>();
	private InputStream facturaXml;
	private InputStream facturaPdf;

	public FacturaResponseSemaphore(int permits, boolean fair) {
		super(permits, fair);
	}

	public InputStream getFacturaXml() {
		return facturaXml;
	}

	public void setFacturaXml(InputStream facturaXml) {
		this.facturaXml = facturaXml;
	}

	public void setFacturaPdf(InputStream facturaPdf) {
		this.facturaPdf = facturaPdf;
	}

	public InputStream getFacturaPdf() {
		return facturaPdf;
	}
}
