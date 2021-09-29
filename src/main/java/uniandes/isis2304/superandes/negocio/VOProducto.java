package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de <B>PRODUCTO</B>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Sebastian Mujica
 */
public interface VOProducto {

	

	/**
	 * @return el codigo de barras.
	 */
	public long getCodigoBarras();
	
	/**
	 * @return el nombre del producto
	 */
	public String getNombre();
	
	/**
	 * @return la marcha del producto
	 */
	public String getMarca();
	
	
	/**
	 * @return la presentacion
	 */
	public String getPresentacion();
	
	/**
	 * @return la uniad de medida del producto
	 */
	public String getUnidadMedida();
	
	/**
	 * @return el empacado del producto
	 */
	public String getEmpacado();
	
	/**
	 * @return la cantidad de presentacion del producto
	 */
	public int getCantidadPresentacion();
	
	/**
	 * @return el volumen del producto
	 */
	public double getVolumenEmpaque();
	
	/**
	 * @return el peso del producto
	 */
	public double getPesoEmpaque();
	
	/**
	 * @return la fecha de vencimiento del producto
	 */
	public String getFechaDeVencimiento();


	/**
	 * @return Una cadena de caracteres con la informaci�n b�sica
	 */
	@Override
	public String toString();

}