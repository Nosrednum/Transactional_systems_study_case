package main.java.uniandes.isis2304.superandes.negocio;

public interface VOPromociones {
	
	/**
	 * @return el identificador del producto de la relación
	 * con la promocion
	 */
	public long getIdProducto();
	
	/**
	 * @return el identificador de la promocion 
	 * que tiene la relacion con el producto
	 */
	public long getIdPromocion();
	
	
	/** 
	 * @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();

}
