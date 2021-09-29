/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class ProductoCategoria implements VOProductoCategoria{

	/**
	 * el identificador del producto
	 */
	private long producto;
	
	/**
	 * el identificador de la categoria
	 * a la cual pertenece el producto
	 */
	private long categoria;
	
	/**
	 * Constructor vacio.
	 */
	public ProductoCategoria() {
		this.producto=0;
		this.categoria=0;
	}


	/**
	 * Constructor con parámetros
	 * @param producto
	 * @param categoria
	 */
	public ProductoCategoria(long producto, long categoria) {
		this.producto = producto;
		this.categoria = categoria;
	}


	/**
	 * @param producto the producto to set
	 */
	public void setProducto(long producto) {
		this.producto = producto;
	}


	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}


	@Override
	public long getProducto() {
		return this.producto;
	}


	@Override
	public long getCategoria() {
		return this.categoria;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductoCategoria [producto=" + producto + ", categoria=" + categoria + "]";
	}
	
	
}
