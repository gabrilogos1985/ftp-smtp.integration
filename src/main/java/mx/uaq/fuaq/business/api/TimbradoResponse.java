package mx.uaq.fuaq.business.api;

import java.io.InputStream;

public class TimbradoResponse {

	private InputStream facturaXml;
	private InputStream facturaPdf;

	public InputStream getFacturaXml() {
		return facturaXml;
	}

	public void setFacturaXml(InputStream facturaXml) {
		this.facturaXml = facturaXml;
	}

	public InputStream getFacturaPdf() {
		return facturaPdf;
	}

	public void setFacturaPdf(InputStream facturaPdf) {
		this.facturaPdf = facturaPdf;
	}

}
