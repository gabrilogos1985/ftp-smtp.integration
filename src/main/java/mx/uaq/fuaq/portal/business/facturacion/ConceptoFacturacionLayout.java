package mx.uaq.fuaq.portal.business.facturacion;

import org.supercsv.ext.annotation.CsvBean;
import org.supercsv.ext.annotation.CsvColumn;
import org.supercsv.ext.annotation.CsvDateConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gabe on 12/28/14.
 */
@CsvBean
public class ConceptoFacturacionLayout {
	@CsvColumn(position = 0, optional = false)
	private String conceptoId = "CON";
	@CsvColumn(position = 1, optional = false)
	private Long cantidad;
	@CsvColumn(position = 2, optional = false)
	private String unidad;
	@CsvColumn(position = 3, optional = false)
	private String claveDescripcion;
	@CsvColumn(position = 4, optional = false)
	private String observacionDetalle;
	@CsvColumn(position = 5, optional = false)
	private BigDecimal valorUnitario;
	@CsvColumn(position = 6, optional = false)
	private Integer impuestoPorcentaje;
	@CsvColumn(position = 7, optional = true)
	private String nombrePedimento;
	@CsvColumn(position = 8, optional = true)
	@CsvDateConverter(locale = "es-MX", pattern = "yyyy-MM-dd")
	private Date fechaPedimentoAduana;
	@CsvColumn(position = 9, optional = true)
	private String nombreAduana;

	public String getConceptoId() {
		return conceptoId;
	}

	public void setCantidad(final Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setUnidad(final String unidad) {
		this.unidad = unidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setClaveDescripcion(final String claveDescripcion) {
		this.claveDescripcion = claveDescripcion;
	}

	public String getClaveDescripcion() {
		return claveDescripcion;
	}

	public void setObservacionDetalle(final String observacionDetalle) {
		this.observacionDetalle = observacionDetalle;
	}

	public String getObservacionDetalle() {
		return observacionDetalle;
	}

	public void setValorUnitario(final BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setImpuestoPorcentaje(final Integer impuestoPorcentaje) {
		this.impuestoPorcentaje = impuestoPorcentaje;
	}

	public Integer getImpuestoPorcentaje() {
		return impuestoPorcentaje;
	}

	public String getNombrePedimento() {
		return nombrePedimento;
	}

	public void setNombrePedimento(final String nombrePedimento) {
		this.nombrePedimento = nombrePedimento;
	}

	public Date getFechaPedimentoAduana() {
		return fechaPedimentoAduana;
	}

	public void setFechaPedimentoAduana(final Date fechaPedimentoAduana) {
		this.fechaPedimentoAduana = fechaPedimentoAduana;
	}

	public String getNombreAduana() {
		return nombreAduana;
	}

	public void setNombreAduana(final String nombreAduana) {
		this.nombreAduana = nombreAduana;
	}
}
