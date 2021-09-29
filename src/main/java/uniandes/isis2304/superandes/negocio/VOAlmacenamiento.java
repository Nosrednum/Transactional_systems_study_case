package main.java.uniandes.isis2304.superandes.negocio;


/**
 * Interfaz para los métodos get de <B>Almacenamiento</B>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragan
 */
public interface VOAlmacenamiento {

	
	/**
	 * @return el identificador de la bodega o el estante.
	 */
	public long getId();
	
	/**
	 * @return el peso actual del estante o la bodega.
	 */
	public double getPeso();
	
	/**
	 * @return el peso máximo que puede soportar el estante 
	 * o la bodega.
	 */
	public double getPesoMax();
	
	/**
	 * @return el volumen actual que está ocupando del almacenamiento.
	 */
	public double getVolumen();
	
	/**
	 * @return El volumen máximo que puede soportar el almacenamiento.
	 */
	public double getVolumenMax();
	
	/**
	 * @return el tipo de almacenamietno que esté en: BODEGA, ESTANTE O CARRITO.
	 */
	public String getTipo();
	
	/**
	 * @return el identificador de la sucursal a la que pertenece
	 * el estante o la bodega.
	 */
	public long getSucursal();
	
	/**
	 * @return el identificador de la categoría a la que pertenece el almacenamiento.
	 */
	public long getCategoria();
	
	/**
	 * @return el nivel de abastecimiento en el que se encuentra la bodega.
	 */
	public long getNivelAbastecimiento();

	/** 
	 * @return Una cadena de caracteres con la información básica 
	 */
	@Override
	public String toString();

}