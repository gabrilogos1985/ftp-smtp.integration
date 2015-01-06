package mx.uaq.fuaq.portal.business.facturacion;

import org.supercsv.ext.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gabe on 12/28/14.
 */
@CsvBean
public class ComprobanteFacturacionLayout {
	@CsvColumn(position = 0, optional = false)
	private String comprobanteIndex = "COM";
	@CsvColumn(position = 1, optional = false)
	private String companyName = "UAQ";
	@CsvColumn(position = 2, optional = false)
	@CsvStringConverter(regex = "^([A-Za-zñÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Za-z|\\d]{2,3})$")
	private String rfcEmisor;
	@CsvColumn(position = 3, optional = false, unique = true)
	@CsvNumberConverter(min = "0", max = "9999999999")
	private Long facturaId;
	@CsvColumn(position = 4, optional = false)
	@CsvDateConverter(locale = "es-MX", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaGeneracion;
	@CsvColumn(position = 5, optional = false)
	private BigDecimal subtotal;
	@CsvColumn(position = 6, optional = false)
	@CsvNumberConverter(min = "1", max = "5")
	private Integer tipoMonedaId;
	@CsvColumn(position = 7, optional = false)
	private BigDecimal total;
	@CsvColumn(position = 8, optional = false)
	@CsvStringConverter(regex = "I|E")
	private String tipoComprobante;
	@CsvColumn(position = 9, optional = false)
	private Integer metodoPagoId;
	@CsvColumn(position = 10, optional = false)
	private Integer bancoId;
	@CsvColumn(position = 11, optional = true)
	private Integer numeroCuentaPago;
	@CsvColumn(position = 12, optional = false)
	private String nombreSucursal;
	@CsvColumn(position = 13, optional = false)
	private String aliasCliente;
	@CsvColumn(position = 14, optional = false)
	@CsvStringConverter(regex = "^([A-Za-zñÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Za-z|\\d]{2,3})$")
	private String rfcReceptor;
	@CsvColumn(position = 15, optional = false)
	private String nombreReceptor;
	@CsvColumn(position = 16, optional = false)
	private String calleReceptor;
	@CsvColumn(position = 17, optional = false)
	private String numeroExteriorReceptor;
	@CsvColumn(position = 18, optional = true)
	private String numeroInteriorReceptor;
	@CsvColumn(position = 19, optional = false)
	private String coloniaReceptor;
	@CsvColumn(position = 20, optional = false)
	private String municipioReceptor;
	@CsvColumn(position = 21, optional = false)
	private Integer estadoReceptor;
	@CsvColumn(position = 22, optional = false)
	private Integer paisReceptor;
	@CsvColumn(position = 23, optional = false)
	private Integer codigoPostalReceptor;
	@CsvColumn(position = 24, optional = false)
	private String nombreContactoReceptor;
	@CsvColumn(position = 25, optional = true)
	private String telefonoContactoReceptor;
	@CsvColumn(position = 26, optional = false)
	private String emailContactoReceptor;
	@CsvColumn(position = 27, optional = false)
	@CsvBooleanConverter(outputFalseValue = "N", outputTrueValue = "S")
	private Boolean envioEmailFactura;
	@CsvColumn(position = 28, optional = true)
	private BigDecimal totalImpuestosRetenidos;
	@CsvColumn(position = 29, optional = true)
	private BigDecimal totalImpuestosTrasladados;
	@CsvColumn(position = 30, optional = true)
	private String observaciones;

	public String getComprobanteIndex() {
		return comprobanteIndex;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setRfcEmisor(final String rfcEmisorParam) {
		this.rfcEmisor = rfcEmisorParam;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setFacturaId(final Long facturaIdentifier) {
		this.facturaId = facturaIdentifier;
	}

	public Long getFacturaId() {
		return facturaId;
	}

	public void setFechaGeneracion(final Date fechaGeneracionParam) {
		this.fechaGeneracion = fechaGeneracionParam;
	}

	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setSubtotal(final BigDecimal subtotalParam) {
		this.subtotal = subtotalParam;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setTipoMonedaId(final Integer tipoMonedaIdParam) {
		this.tipoMonedaId = tipoMonedaIdParam;
	}

	public Integer getTipoMonedaId() {
		return tipoMonedaId;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTipoComprobante(final String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setMetodoPagoId(final Integer metodoPagoId) {
		this.metodoPagoId = metodoPagoId;
	}

	public Integer getMetodoPagoId() {
		return metodoPagoId;
	}

	public void setBancoId(final Integer bancoId) {
		this.bancoId = bancoId;
	}

	public Integer getBancoId() {
		return bancoId;
	}

	public Integer getNumeroCuentaPago() {
		return numeroCuentaPago;
	}

	public void setNumeroCuentaPago(final Integer numeroCuentaPago) {
		this.numeroCuentaPago = numeroCuentaPago;
	}

	public void setNombreSucursal(final String nombreSucursalParam) {
		this.nombreSucursal = nombreSucursalParam;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setAliasCliente(final String aliasClienteParam) {
		this.aliasCliente = aliasClienteParam;
	}

	public String getAliasCliente() {
		return aliasCliente;
	}

	public void setRfcReceptor(final String rfcReceptorParam) {
		this.rfcReceptor = rfcReceptorParam;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setNombreReceptor(final String nombreReceptorParam) {
		this.nombreReceptor = nombreReceptorParam;
	}

	public String getNombreReceptor() {
		return nombreReceptor;
	}

	public void setCalleReceptor(final String calleReceptorParam) {
		this.calleReceptor = calleReceptorParam;
	}

	public String getCalleReceptor() {
		return calleReceptor;
	}

	public void setNumeroExteriorReceptor(final String numeroExteriorParam) {
		this.numeroExteriorReceptor = numeroExteriorParam;
	}

	public String getNumeroExteriorReceptor() {
		return numeroExteriorReceptor;
	}

	public void setColoniaReceptor(final String coloniaReceptorParam) {
		this.coloniaReceptor = coloniaReceptorParam;
	}

	public String getColoniaReceptor() {
		return coloniaReceptor;
	}

	public void setMunicipioReceptor(final String municipioReceptorParam) {
		this.municipioReceptor = municipioReceptorParam;
	}

	public String getMunicipioReceptor() {
		return municipioReceptor;
	}

	public void setEstadoReceptor(final Integer estadoReceptor) {
		this.estadoReceptor = estadoReceptor;
	}

	public Integer getEstadoReceptor() {
		return estadoReceptor;
	}

	public void setPaisReceptor(final Integer paisReceptor) {
		this.paisReceptor = paisReceptor;
	}

	public Integer getPaisReceptor() {
		return paisReceptor;
	}

	public void setNombreContactoReceptor(final String nombreContactoReceptor) {
		this.nombreContactoReceptor = nombreContactoReceptor;
	}

	public String getNombreContactoReceptor() {
		return nombreContactoReceptor;
	}

	public void setEmailContactoReceptor(final String emailContactoReceptor) {
		this.emailContactoReceptor = emailContactoReceptor;
	}

	public String getEmailContactoReceptor() {
		return emailContactoReceptor;
	}

	public String getNumeroInteriorReceptor() {
		return numeroInteriorReceptor;
	}

	public void setNumeroInteriorReceptor(final String numeroInteriorReceptor) {
		this.numeroInteriorReceptor = numeroInteriorReceptor;
	}

	public String getTelefonoContactoReceptor() {
		return telefonoContactoReceptor;
	}

	public void setTelefonoContactoReceptor(final String telefonoContactoReceptor) {
		this.telefonoContactoReceptor = telefonoContactoReceptor;
	}

	public Boolean getEnvioEmailFactura() {
		return envioEmailFactura;
	}

	public void setEnvioEmailFactura(final Boolean envioEmailFactura) {
		this.envioEmailFactura = envioEmailFactura;
	}

	public BigDecimal getTotalImpuestosRetenidos() {
		return totalImpuestosRetenidos;
	}

	public void setTotalImpuestosRetenidos(final BigDecimal totalImpuestosRetenidos) {
		this.totalImpuestosRetenidos = totalImpuestosRetenidos;
	}

	public BigDecimal getTotalImpuestosTrasladados() {
		return totalImpuestosTrasladados;
	}

	public void setTotalImpuestosTrasladados(final BigDecimal totalImpuestosTrasladados) {
		this.totalImpuestosTrasladados = totalImpuestosTrasladados;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

	public void setCodigoPostalReceptor(final Integer codigoPostalReceptor) {
		this.codigoPostalReceptor = codigoPostalReceptor;
	}

	public Integer getCodigoPostalReceptor() {
		return codigoPostalReceptor;
	}
}
