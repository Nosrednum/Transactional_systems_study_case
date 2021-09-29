package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de <b>SUCURSAL</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Sebastian Mujica
 */
public interface VOProductosPedidos {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	

	/**
	 * @return el id del producto pedido.
	 */
	public long getIdProducto();
	
	/**
	 * @return el id del pedido al cual 
	 * pertenece el producto pedido.
	 */
	public long getIdPedido();
	
	/**
	 * @return la cantidad de productos pedidos
	 * en el pedido.
	 */
	public int getCantidadRecompra();

	/** 
	 * @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
