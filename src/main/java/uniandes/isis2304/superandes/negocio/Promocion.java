/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * @author Sebastian Mujica
 *
 */
public class Promocion implements VOPromocion{

	/**
	 *  El identificador de la promocion
	 */
	private long id;

	/**
	 * la descripcion de la promocion
	 */
	private String descripcion;

	/**
	 * la fecha en la que termina la promcion
	 */
	private String fechaFin;

	/**
	 * la cantidad de productos en la promocion
	 * esta cantidad puede ser nula.
	 */
	private int cantidadProductos;

	/**
	 * Constructor vacío
	 */
	public Promocion() {
	}

	/**
	 * Constructor con parámetros
	 * @param id
	 * @param descripcion
	 * @param fechaFin
	 * @param cantidadProductos
	 */
	public Promocion(long id, String descripcion, String fechaFin, int cantidadProductos) {
		this.id = id;
		this.descripcion = descripcion;
		this.fechaFin = fechaFin;
		this.cantidadProductos = cantidadProductos;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @param cantidadProductos the cantidadProductos to set
	 */
	public void setCantidadProductos(int cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getDescripcion() {
		return this.descripcion;
	}

	@Override
	public String getFechaFin() {
		return this.fechaFin;
	}

	@Override
	public int getCantidadProductos() {
		return this.cantidadProductos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Promocion [id=" + id + ", descripcion=" + descripcion + ", fechaFin=" + fechaFin
				+ ", cantidadProductos=" + cantidadProductos + "]";
	}





}
