/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class Promociones implements VOPromociones {
	
	/**
	 * El id del producto que tiene una promocion
	 */
	private long idProducto;
	
	/**
	 * representa una promocion en la tienda.
	 */
	private long idPromocion;
	
	/**
	 * constructor vacio
	 */
	public Promociones() {
		this.idProducto=0;
		this.idPromocion=0;
	}

	/**
	 * Constructor con parametros
	 * @param idProducto
	 * @param idPromocion
	 */
	public Promociones(long idProducto, long idPromocion) {
		this.idProducto = idProducto;
		this.idPromocion = idPromocion;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @param idPromocion the idPromocion to set
	 */
	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
	}

	@Override
	public long getIdProducto() {
		return this.idProducto;
	}

	@Override
	public long getIdPromocion() {
		return this.idPromocion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Promociones [idProducto=" + idProducto + ", idPromocion=" + idPromocion + "]";
	}
	
	
	
	

}
