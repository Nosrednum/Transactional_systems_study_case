package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de <b>ProductoSucursal</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOProductoSucursal {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	/**
	 * @return identificador de la sucursal
	 * a la que pertenece la relacion
	 */
	public long getSucursal();
	
	/**
	 * @return identificador del producto al que pertence
	 * la relación	
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
	 * @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
