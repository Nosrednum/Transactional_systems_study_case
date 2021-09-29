/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class Producto implements VOProducto{

	/**
	 * nombre del producto
	 */
	private String nombre;

	/**
	 * Marca del producto
	 */
	private String marca;

	/**
	 * presentacion del producto
	 */
	private String presentacion;

	/**
	 * unidad de medida del producto
	 */
	private String unidadMedida;

	/**
	 * empaque del producto
	 */
	private String empacado;

	/**
	 * cantidad de la prsentación 
	 */
	private int cantidadPresentacion;

	/**
	 * volumen del empaque dle producto.
	 */
	private double volumenEmpaque;

	/**
	 * peso del empaque del producto.
	 */
	private double pesoEmpaque;

	/**
	 * fecha de vencimiento del producto.
	 */
	private String fechaDeVencimiento;

	/**
	 * Codigo de barras del producto.
	 */
	private long codigoBarras;

	/**
	 * Constructor vacío
	 */
	public Producto() {
		this.nombre="";
		this.marca="";
		this.presentacion="";
		this.unidadMedida="";
		this.empacado="";
		this.cantidadPresentacion=0;
		this.volumenEmpaque=0;
		this.pesoEmpaque=0;
		this.fechaDeVencimiento= "";
		this.codigoBarras=0;
	}

	/**
	 * Constructor con parámetros.
	 * @param nombre
	 * @param marca
	 * @param presentacion
	 * @param unidadMedida
	 * @param empacado
	 * @param cantidadPresentacion
	 * @param volumenEmpaque
	 * @param pesoEmpaque
	 * @param fechaDeVencimiento
	 * @param codigoBarras
	 */
	public Producto(String nombre, String marca, String presentacion, String unidadMedida, String empacado,
			int cantidadPresentacion, double volumenEmpaque, double pesoEmpaque, String fechaDeVencimiento,
			long codigoBarras) {
		this.nombre = nombre;
		this.marca = marca;
		this.presentacion = presentacion;
		this.unidadMedida = unidadMedida;
		this.empacado = empacado;
		this.cantidadPresentacion = cantidadPresentacion;
		this.volumenEmpaque = volumenEmpaque;
		this.pesoEmpaque = pesoEmpaque;
		this.fechaDeVencimiento = fechaDeVencimiento;
		this.codigoBarras = codigoBarras;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @param presentacion the presentacion to set
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * @param empacado the empacado to set
	 */
	public void setEmpacado(String empacado) {
		this.empacado = empacado;
	}

	/**
	 * @param cantidadPresentacion the cantidadPresentacion to set
	 */
	public void setCantidadPresentacion(int cantidadPresentacion) {
		this.cantidadPresentacion = cantidadPresentacion;
	}

	/**
	 * @param volumenEmpaque the volumenEmpaque to set
	 */
	public void setVolumenEmpaque(double volumenEmpaque) {
		this.volumenEmpaque = volumenEmpaque;
	}

	/**
	 * @param pesoEmpaque the pesoEmpaque to set
	 */
	public void setPesoEmpaque(double pesoEmpaque) {
		this.pesoEmpaque = pesoEmpaque;
	}

	/**
	 * @param fechaDeVencimiento the fechaDeVencimiento to set
	 */
	public void setFechaDeVencimiento(String fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}

	/**
	 * @param codigoBarras the codigoBarras to set
	 */
	public void setCodigoBarras(long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	@Override
	public long getCodigoBarras() {
		return this.codigoBarras;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getMarca() {
		return this.marca;
	}

	@Override
	public String getPresentacion() {
		return this.presentacion;
	}

	@Override
	public String getUnidadMedida() {
		return this.unidadMedida;
	}

	@Override
	public String getEmpacado() {
		return this.empacado;
	}

	@Override
	public int getCantidadPresentacion() {
		return this.cantidadPresentacion;
	}

	@Override
	public double getVolumenEmpaque() {
		return this.volumenEmpaque;
	}

	@Override
	public double getPesoEmpaque() {
		return this.pesoEmpaque;
	}

	@Override
	public String getFechaDeVencimiento() {
		return this.fechaDeVencimiento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", marca=" + marca + ", presentacion=" + presentacion + ", unidadMedida="
				+ unidadMedida + ", empacado=" + empacado + ", cantidadPresentacion=" + cantidadPresentacion
				+ ", volumenEmpaque=" + volumenEmpaque + ", pesoEmpaque=" + pesoEmpaque + ", fechaDeVencimiento="
				+ fechaDeVencimiento + ", codigoBarras=" + codigoBarras + "]";
	}







}
