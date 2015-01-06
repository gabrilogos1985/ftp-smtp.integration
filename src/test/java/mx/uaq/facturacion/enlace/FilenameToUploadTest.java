package mx.uaq.facturacion.enlace;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FilenameToUploadTest {

	@Test
	public void test() {
		String keyFactura = "2015-01-03T10:11:22";
		assertThat(new FacturacionGateway().createFilename(keyFactura),
				equalTo("UAQ_AAA010101AAA_20150103101122.txt"));
	}

}
