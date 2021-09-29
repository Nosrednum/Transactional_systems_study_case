package main.java.uniandes.isis2304.superandes.negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz para los métodos get de <b>Categoria</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOCategoria {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	/** @return 
	 * El id de la Categoria 
	 */
	public long getId();

	/** 
	 * @return la categoría a la que representa dentro de
	 * las posibles categorías.
	 */
	public String getCategoria();

	/** 
	 * @return una lista con todos los productos pertenecinetes a la categoría
	 */
	public List< Object []> getProductosCategoria();

	/** 
	 * @return Una cadena de caracteres con la informacion básica.
	 */
	@Override
	public String toString();
	
	/**
	 * @return una cadena de caracteres con la información básica
	 * de una categoría, además contiene los productos pertenecientes
	 * a la categoría, uno por línea.
	 */
	public String toStringCompleto();
}
