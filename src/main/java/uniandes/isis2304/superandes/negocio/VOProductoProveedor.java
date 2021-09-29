package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de <b>ProductoProveedor</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOProductoProveedor {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	/**
	 * @return la calificación de calidad.
	 */
	public int getCalificacionCalidad();
	
	/**
	 * @return  el precio del producto dado por el proveedor
	 */
	public double getPrecioProdProvee();
	
	/**
	 * @return el identificador del proveedor
	 */
	public long getIdProveedor();
	
	/**
	 * @return el identificador del producto
	 */
	public long getIdProducto();
	

	/**
	 * @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
