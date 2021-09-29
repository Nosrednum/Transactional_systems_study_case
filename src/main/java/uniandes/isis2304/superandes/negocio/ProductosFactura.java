/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class ProductosFactura implements VOProductosFactura {
	
	private long factura;
	
	private long producto;
	
	private int cantidad;
	
	/**
	 * Constructor vacío.
	 */
	public ProductosFactura() {
		this.factura=0;
		this.producto=0;
		this.cantidad=0;
	}

	/**
	 * Constructor con parámetros.
	 * @param factura
	 * @param producto
	 * @param cantidad
	 */
	public ProductosFactura(long factura, long producto, int cantidad) {
		this.factura = factura;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	/**
	 * @param factura the factura to set
	 */
	public void setFactura(long factura) {
		this.factura = factura;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(long producto) {
		this.producto = producto;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public long getFactura() {
		return this.factura;
	}

	@Override
	public long getProducto() {
		return this.producto;
	}

	@Override
	public int getCantidad() {
		return this.cantidad;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductosFactura [factura=" + factura + ", producto=" + producto + ", cantidad=" + cantidad + "]";
	}

}
