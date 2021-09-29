package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de <b>SUCURSAL</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOPedido {

	/* ****************************************************************
	 * 			Métodos 
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
	 * @return la evaluación de la cantidad del pedido.
	 */
	public int getEvCant();

	/**
	 * @return la evaluación de la calidad del pedido.
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
	 * @return el identificador de la sucursal que realizó el pedido.
	 */
	public long getSucursal();


	/**
	 * @return una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
