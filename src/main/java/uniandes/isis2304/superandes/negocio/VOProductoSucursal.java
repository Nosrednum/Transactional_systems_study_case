package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los m�todos get de <b>ProductoSucursal</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOProductoSucursal {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return identificador de la sucursal
	 * a la que pertenece la relacion
	 */
	public long getSucursal();
	
	/**
	 * @return identificador del producto al que pertence
	 * la relaci�n	
	 */
	public long getProducto();
	
	/**
	 * @return nivel de reorden que debe tener el producto 
	 * dentro de las bodegas.
	 */
	public int getNivReorden();
	
	/**
	 * @return precio unitario del producto dado en la sucursal
	 * a la que corresponde.
	 */
	public double getPrecioUnitario();
	
	/**
	 * @return precio del producto en la sucursal correspondiente.
	 */
	public double getPrecioSucursal();
	
	/**
	 * @return precio por unidad de medida del producto en la 
	 * sucursal correspondiente
	 */
	public double getPrecioUnidadMedida();
	
	
	/** 
	 * @return Una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
}
