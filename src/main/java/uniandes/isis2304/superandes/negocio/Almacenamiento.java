/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Anderson Barragán
 *
 */
public class Almacenamiento implements VOAlmacenamiento{

	public static final String TIPO_BODEGA 	= "BODEGA"	;
	public static final String TIPO_ESTANTE = "ESTANTE"	;
	public static final String TIPO_CARRITO = "CARRITO"	;

	/**
	 * id del estante o bodega.
	 */
	private long id;

	/**
	 * peso actual del almacenameinto.
	 */
	private double peso;

	/**
	 * peso máximo que puede soportar el almacenameinto.
	 */
	private double peso_Max;

	/**
	 * volumen actual del almacenameinto.
	 */
	private double volumen;

	/**
	 * volumen máximo que puede soportar
	 * el almacenameinto.
	 */
	private double volumen_Max;

	/**
	 * tipo al que pertenece el almacenamiento
	 * puede ser estante o bodega.
	 */
	private String tipo;

	/**
	 * sucursal a la cual pertenece el 
	 * almacenamiento.
	 */
	private long sucursal;

	/**
	 * categoría a la cual pertenece 
	 * el almacenamiento.
	 */
	private long categoria;

	/**
	 * nivel de mínimo de productos que puede
	 * llegar a tener un estatne
	 */
	private int  nivelAbastecimiento;

	/**
	 * Constructor vacío.
	 */
	public Almacenamiento() {
		this.id=0;
		this.peso=0;
		this.peso_Max=0;
		this.volumen=0;
		this.volumen_Max=0;
		this.tipo="";
		this.sucursal=0;
		this.categoria=0;
		this.nivelAbastecimiento=0;

	}

	/**
	 * Constructor con parámetros.
	 * @param id
	 * @param peso
	 * @param peso_Max
	 * @param volumen
	 * @param volmenMax
	 * @param tipo
	 * @param sucursal
	 * @param categoria
	 * @param nivelAbastecimiento
	 */
	public Almacenamiento(long id, double peso, double peso_Max, double volumen, double volmenMax, String tipo,
			long sucursal, long categoria, int nivelAbastecimiento) {
		this.id = id;
		this.peso = peso;
		this.peso_Max = peso_Max;
		this.volumen = volumen;
		this.volumen_Max = volmenMax;
		this.tipo = tipo;
		this.sucursal = sucursal;
		this.categoria = categoria;
		this.nivelAbastecimiento = nivelAbastecimiento;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * @param peso_Max the peso_Max to set
	 */
	public void setpeso_Max(double peso_Max) {
		this.peso_Max = peso_Max;
	}

	/**
	 * @param volumen the volumen to set
	 */
	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	/**
	 * @param volmenMax the volmenMax to set
	 */
	public void setVolmenMax(double volmenMax) {
		this.volumen_Max = volmenMax;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}

	/**
	 * @param nivelAbastecimiento the nivelAbastecimiento to set
	 */
	public void setNivelAbastecimiento(int nivelAbastecimiento) {
		this.nivelAbastecimiento = nivelAbastecimiento;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public double getPeso() {
		return this.peso;
	}

	@Override
	public double getPesoMax() {
		return this.peso_Max;
	}

	@Override
	public double getVolumen() {
		return this.volumen;
	}

	@Override
	public double getVolumenMax() {
		return this.volumen_Max;
	}

	@Override
	public String getTipo() {
		return this.tipo;
	}

	@Override
	public long getSucursal() {
		return this.sucursal;
	}

	@Override
	public long getCategoria() {
		return this.categoria;
	}

	@Override
	public long getNivelAbastecimiento() {
		return this.nivelAbastecimiento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Almacenamiento [id=" + id + ", peso=" + peso + ", peso_Max=" + peso_Max + ", volumen=" + volumen
				+ ", volmenMax=" + volumen_Max + ", tipo=" + tipo + ", sucursal=" + sucursal + ", categoria=" + categoria
				+ ", nivelAbastecimiento=" + nivelAbastecimiento + "]";
	}




}
