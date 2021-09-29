package main.java.uniandes.isis2304.superandes.negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz para los m�todos get de <b>Categoria</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOCategoria {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/** @return 
	 * El id de la Categoria 
	 */
	public long getId();

	/** 
	 * @return la categor�a a la que representa dentro de
	 * las posibles categor�as.
	 */
	public String getCategoria();

	/** 
	 * @return una lista con todos los productos pertenecinetes a la categor�a
	 */
	public List< Object []> getProductosCategoria();

	/** 
	 * @return Una cadena de caracteres con la informacion b�sica.
	 */
	@Override
	public String toString();
	
	/**
	 * @return una cadena de caracteres con la informaci�n b�sica
	 * de una categor�a, adem�s contiene los productos pertenecientes
	 * a la categor�a, uno por l�nea.
	 */
	public String toStringCompleto();
}
