package main.java.uniandes.isis2304.superandes.negocio;

/**
 * 
 * @author Anderson Barragán
 *
 */
public class Factura implements VOFactura{

	/**
	 * Representa el número de factura.
	 */
	private long numeroFactura;

	/**
	 * Representa la fecha de la factura.
	 */
	private String fechaFactura;

	/**
	 * representa la descripción de la factura.
	 */
	private String descripcion;

	/**
	 * Representa el total de la factura.
	 */
	private double total;

	/**
	 * Representa la sucursal a la que pertenece
	 * la factura.
	 */
	private long deSucursal;

	/**
	 * Represnta al cliente a quien pertenece
	 * la factura.
	 */
	private long deCliente;

	/**
	 * Constructor vacío.
	 */
	public Factura() {
		this.numeroFactura=0;
		this.fechaFactura= new String();
		this.descripcion = new String();
		this.total=0;
		this.deSucursal=0;
		this.deCliente=0;
	}



	/**
	 * Constructor con valores.
	 * @param numeroFactura
	 * @param fechaFactura
	 * @param descripcion
	 * @param total
	 * @param deSucursal
	 * @param deCliente
	 */
	public Factura(long numeroFactura, String fechaFactura, long deSucursal,
			long deCliente) {
		this.numeroFactura = numeroFactura;
		this.fechaFactura = fechaFactura;
		this.deSucursal = deSucursal;
		this.deCliente = deCliente;
	}



	@Override
	public long getNumeroFactura() {
		return this.numeroFactura;
	}

	@Override
	public String getFechaFactura() {
		return this.fechaFactura;
	}

	@Override
	public long getDeCliente() {
		return this.deCliente;
	}

	@Override
	public long getDeSucursal() {
		return this.deSucursal;
	}

	/**
	 * @param numeroFactura the numeroFactura to set
	 */
	public void setNumeroFactura(long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	/**
	 * @param fechaFactura the fechaFactura to set
	 */
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @param deSucursal the deSucursal to set
	 */
	public void setDeSucursal(long deSucursal) {
		this.deSucursal = deSucursal;
	}

	/**
	 * @param deCliente the deCliente to set
	 */
	public void setDeCliente(long deCliente) {
		this.deCliente = deCliente;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Factura [id=" + this.numeroFactura + ", fechaFactura=" + this.fechaFactura.toString() +
				", descripcion=" + this.descripcion + ", total=" + this.total + ", deSucursal=" + this.deSucursal +
				",deCliente=" + this.deCliente + "]";
	}

}
