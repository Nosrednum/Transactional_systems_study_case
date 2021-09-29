package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de <b>SUCURSAL</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOPedido {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return el identificador del pedido	
	 */
	public long getIdPedido();

	/**
	 * @return el identificador del producto.
	 */
	public long getIdProducto();

	/**
	 * @return la fecha de entrega del pedido.
	 */
	public Date getFechaEntrega();

	/**
	 * @return la evaluaci�n de la cantidad del pedido.
	 */
	public int getEvCant();

	/**
	 * @return la evaluaci�n de la calidad del pedido.
	 */
	public int getEvCalidad();

	/**
	 * @return el precio acordado del pedido.
	 */
	public double getPrecioPedido();

	/**
	 * @return la fecha de entrega acordada.
	 */
	public Date getFechaAcordada();

	/**
	 * @return el proveedor del pedido.
	 */
	public long getProveedor();

	/**
	 * @return el identificador de la sucursal que realiz� el pedido.
	 */
	public long getSucursal();


	/**
	 * @return una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
}
