/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class ProductoSucursal implements VOProductoSucursal{
	
	/**
	 * identificador de la sucursal a la 
	 * cual pertenece el producto.
	 */
	private long sucursal;

	/**
	 * identificador del producto.
	 */
	private long producto;

	/**
	 * nivel de reorden del producto 
	 * en la sucursal
	 */
	private int nivReorden;

	/**
	 * el precio unitario del 
	 * producto en la sucursal
	 */
	private double precioUnitario;

	/**
	 * el precio del producto 
	 * en la sucursal.
	 */
	private double precioSucursal;

	/**
	 * el precio por unidad de medida
	 * del producto en la sucursal.
	 */
	private double precioUnidadMedida;
	
	/**
	 * Constructor vacío.
	 */
	public ProductoSucursal() {
		this.sucursal=0;
		this.producto=0;
		this.nivReorden=0;
		this.precioUnitario=0;
		this.precioSucursal=0;
		this.precioUnidadMedida=0;
	}

	/**
	 * Constructor con parámetros
	 * @param sucursal
	 * @param producto
	 * @param nivReorden
	 * @param precioUnitario
	 * @param precioSucursal
	 * @param precioUnidadMedida
	 */
	public ProductoSucursal(long sucursal, long producto, int nivReorden, double precioUnitario, double precioSucursal,
			double precioUnidadMedida) {
		this.sucursal = sucursal;
		this.producto = producto;
		this.nivReorden = nivReorden;
		this.precioUnitario = precioUnitario;
		this.precioSucursal = precioSucursal;
		this.precioUnidadMedida = precioUnidadMedida;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(long producto) {
		this.producto = producto;
	}

	/**
	 * @param nivReorden the nivReorden to set
	 */
	public void setNivReorden(int nivReorden) {
		this.nivReorden = nivReorden;
	}

	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @param precioSucursal the precioSucursal to set
	 */
	public void setPrecioSucursal(double precioSucursal) {
		this.precioSucursal = precioSucursal;
	}

	/**
	 * @param precioUnidadMedida the precioUnidadMedida to set
	 */
	public void setPrecioUnidadMedida(double precioUnidadMedida) {
		this.precioUnidadMedida = precioUnidadMedida;
	}

	@Override
	public long getSucursal() {
		return this.sucursal;
	}

	@Override
	public long getProducto() {
		return this.producto;
	}

	@Override
	public int getNivReorden() {
		return this.nivReorden;
	}

	@Override
	public double getPrecioUnitario() {
		return this.precioUnitario;
	}

	@Override
	public double getPrecioSucursal() {
		return this.precioSucursal;
	}

	@Override
	public double getPrecioUnidadMedida() {
		return this.precioUnidadMedida;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductoSucursal [sucursal=" + sucursal + ", producto=" + producto + ", nivReorden=" + nivReorden
				+ ", precioUnitario=" + precioUnitario + ", precioSucursal=" + precioSucursal + ", precioUnidadMedida="
				+ precioUnidadMedida + "]";
	}
	
	
	
	

}
