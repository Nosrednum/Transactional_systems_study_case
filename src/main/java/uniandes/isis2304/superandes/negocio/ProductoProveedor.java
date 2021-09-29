package main.java.uniandes.isis2304.superandes.negocio;

/**
 * 
 * @author Sebastian Mujica
 *
 */
public class ProductoProveedor implements VOProductoProveedor{
	

	/**
	 * Calificación de la calidad de un producto de un proveedor
	 */
	private int calificacionCalidad;
	
	/**
	 * El precio del producto dado por el proveedor
	 */
	private double precioProdProvee;
	
	/**
	 * el identificador del proveedor
	 */
	private long idProveedor;
	
	/**
	 * el identificador del producto que provee 
	 * ese provedor.
	 */
	private long idProducto;
	
	/**
	 * Constructor vacío.
	 */
	public ProductoProveedor() {
		this.calificacionCalidad=0;
		this.precioProdProvee=0;
		this.idProveedor=0;
		this.idProducto=0;
	}

	/**
	 * Constructor usando parámetros
	 * @param calificacionActual
	 * @param precioProdProvee
	 * @param idProveedor
	 * @param idProducto
	 */
	public ProductoProveedor(int calificacionCalidad, double precioProdProvee, long idProveedor, long idProducto) {
		this.calificacionCalidad = calificacionCalidad;
		this.precioProdProvee = precioProdProvee;
		this.idProveedor = idProveedor;
		this.idProducto = idProducto;
	}

	/**
	 * @param calificacionActual the calificacionActual to set
	 */
	public void setCalificacionActual(int calificacionActual) {
		this.calificacionCalidad = calificacionActual;
	}

	/**
	 * @param precioProdProvee the precioProdProvee to set
	 */
	public void setPrecioProdProvee(double precioProdProvee) {
		this.precioProdProvee = precioProdProvee;
	}

	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public int getCalificacionCalidad() {
		return this.calificacionCalidad;
	}

	@Override
	public double getPrecioProdProvee() {
		return this.precioProdProvee;
	}

	@Override
	public long getIdProveedor() {
		return this.idProveedor;
	}

	@Override
	public long getIdProducto() {
		return this.idProducto;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductoProveedor [calificacionActual=").append(calificacionCalidad)
				.append(", precioProdProvee=").append(precioProdProvee).append(", idProveedor=").append(idProveedor)
				.append(", idProducto=").append(idProducto).append("]");
		return builder.toString();
	}

	

}
