package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de <b>Promocion</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOPromocion {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	/**
	 * @return el id de una promoción
	 */
	public long getId();

	/**
	 * @return la descripción de una promoción
	 */
	public String getDescripcion();

	/**
	 * @return la fecha final de una promocion	
	 */
	public String getFechaFin();

	/**
	 * @return la cantidad de productos que existen 
	 * en promoción.
	 */
	public int getCantidadProductos();



	/** 
	 * @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
