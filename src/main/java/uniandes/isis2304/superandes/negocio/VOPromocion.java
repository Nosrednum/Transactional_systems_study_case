package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de <b>Promocion</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOPromocion {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return el id de una promoci�n
	 */
	public long getId();

	/**
	 * @return la descripci�n de una promoci�n
	 */
	public String getDescripcion();

	/**
	 * @return la fecha final de una promocion	
	 */
	public String getFechaFin();

	/**
	 * @return la cantidad de productos que existen 
	 * en promoci�n.
	 */
	public int getCantidadProductos();



	/** 
	 * @return Una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
}
