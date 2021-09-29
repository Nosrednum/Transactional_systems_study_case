package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los m�todos get de <b>SUCURSAL</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOProductosFactura {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return el identificador de la factura.
	 */
	public long getFactura();
	
	/**
	 * @return el identificador del producto.
	 */
	public long getProducto();
	
	/**
	 * @return la cantidad del producto.
	 */
	public int getCantidad();


	/**
	 * @return una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
}
