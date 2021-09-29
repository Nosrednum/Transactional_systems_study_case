/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class Sucursal implements VOSucursal {

	/**
	 * el id de la sucursal.
	 */
	private long idSucursal;

	/**
	 * el nombre de la sucursal
	 */
	private String nombre; 

	/**
	 * la direccion de la sucursal
	 */
	private String direccion;

	/**
	 * el segmento del mercado que tiene la sucursal
	 */
	private String segmentoMercado;

	/**
	 * el tamanio de la sucursal en metros cuadrados
	 */
	private double tamanio;

	/**
	 * la ciudad en la que se encuentra la sucursal
	 */
	private String ciudad;
	
	/**
	 * Constructor vacío
	 */
	public Sucursal() {
		this.idSucursal=0;
		this.nombre="";
		this.direccion="";
		this.segmentoMercado="";
		this.tamanio=0;
		this.ciudad="";
	}

	/**
	 * Constructor con parámetros.
	 * @param idSucursal
	 * @param nombre
	 * @param direccion
	 * @param segmentoMercado
	 * @param tamanio
	 * @param ciudad
	 */
	public Sucursal(long idSucursal, String nombre, String direccion, String segmentoMercado, double tamanio,
			String ciudad) {
		this.idSucursal = idSucursal;
		this.nombre = nombre;
		this.direccion = direccion;
		this.segmentoMercado = segmentoMercado;
		this.tamanio = tamanio;
		this.ciudad = ciudad;
	}

	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @param segmentoMercado the segmentoMercado to set
	 */
	public void setSegmentoMercado(String segmentoMercado) {
		this.segmentoMercado = segmentoMercado;
	}

	/**
	 * @param tamanio the tamanio to set
	 */
	public void setTamanio(double tamanio) {
		this.tamanio = tamanio;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public long getId() {
		return this.idSucursal;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getDireccion() {
		return this.direccion;
	}

	@Override
	public String getCiudad() {
		return this.ciudad;
	}

	@Override
	public String getSegmentoDeMercado() {
		return this.segmentoMercado;
	}

	@Override
	public double getTamanio() {
		return this.tamanio;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sucursal [idSucursal=" + idSucursal + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", segmentoMercado=" + segmentoMercado + ", tamanio=" + tamanio + ", ciudad=" + ciudad + "]";
	}
	
	
	
	
	
}
