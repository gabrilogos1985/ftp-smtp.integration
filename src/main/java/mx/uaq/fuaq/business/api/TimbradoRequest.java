package mx.uaq.fuaq.business.api;

public class TimbradoRequest {

	private String payload;
	private String idFactura;

	public String getPayload() {
		return payload;
	}

	public void setPayload(final String payload) {
		this.payload = payload;
	}

	public void setIdFactura(String keyLayoutFactura) {
		this.idFactura = keyLayoutFactura;
	}

	public String getIdFactura() {
		return idFactura;
	}

}
