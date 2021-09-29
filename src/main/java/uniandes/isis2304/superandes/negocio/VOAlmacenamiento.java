package main.java.uniandes.isis2304.superandes.negocio;


/**
 * Interfaz para los m�todos get de <B>Almacenamiento</B>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
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
	 * @return el peso m�ximo que puede soportar el estante 
	 * o la bodega.
	 */
	public double getPesoMax();
	
	/**
	 * @return el volumen actual que est� ocupando del almacenamiento.
	 */
	public double getVolumen();
	
	/**
	 * @return El volumen m�ximo que puede soportar el almacenamiento.
	 */
	public double getVolumenMax();
	
	/**
	 * @return el tipo de almacenamietno que est� en: BODEGA, ESTANTE O CARRITO.
	 */
	public String getTipo();
	
	/**
	 * @return el identificador de la sucursal a la que pertenece
	 * el estante o la bodega.
	 */
	public long getSucursal();
	
	/**
	 * @return el identificador de la categor�a a la que pertenece el almacenamiento.
	 */
	public long getCategoria();
	
	/**
	 * @return el nivel de abastecimiento en el que se encuentra la bodega.
	 */
	public long getNivelAbastecimiento();

	/** 
	 * @return Una cadena de caracteres con la informaci�n b�sica 
	 */
	@Override
	public String toString();

}