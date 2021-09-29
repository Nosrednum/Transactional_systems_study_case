package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los m�todos get de <b>ProductoProveedor</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOProductoProveedor {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return la calificaci�n de calidad.
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
	 * @return Una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
}
