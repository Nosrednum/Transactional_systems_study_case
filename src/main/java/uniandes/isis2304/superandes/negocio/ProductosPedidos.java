/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class ProductosPedidos implements VOProductosPedidos {

	/**
	 * identificador del producto pedido.
	 */
	private long idProducto;

	/**
	 * identificador del pedido que se realizò
	 */
	private long idPedido;

	/**
	 * cantidad de productos que se compraron en el pedido.
	 */
	private int cantidadRecompra;

	/**
	 * Constructor vacio.
	 */
	public ProductosPedidos() {
		this.idProducto=0;
		this.idPedido=0;
		this.cantidadRecompra=0;
	}

	/**
	 * Constructor con parametros
	 * @param idProducto
	 * @param idPedido
	 * @param cantidadRecompra
	 */
	public ProductosPedidos(long idProducto, long idPedido, int cantidadRecompra) {
		this.idProducto = idProducto;
		this.idPedido = idPedido;
		this.cantidadRecompra = cantidadRecompra;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @param cantidadRecompra the cantidadRecompra to set
	 */
	public void setCantidadRecompra(int cantidadRecompra) {
		this.cantidadRecompra = cantidadRecompra;
	}

	@Override
	public long getIdProducto() {
		return this.idProducto;
	}

	@Override
	public long getIdPedido() {
		return this.idPedido;
	}

	@Override
	public int getCantidadRecompra() {
		return this.cantidadRecompra;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductosPedidos [idProducto=" + idProducto + ", idPedido=" + idPedido + ", cantidadRecompra="
				+ cantidadRecompra + "]";
	}
	
	
	
}
