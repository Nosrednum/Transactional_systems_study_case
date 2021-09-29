package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de <b>SUCURSAL</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOProductosFactura {

	/* ****************************************************************
	 * 			Métodos 
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
	 * @return una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
