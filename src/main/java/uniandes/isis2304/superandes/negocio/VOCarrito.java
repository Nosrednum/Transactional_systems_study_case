package main.java.uniandes.isis2304.superandes.negocio;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz para los m�todos get de <b>Categoria</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOCarrito {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	/** @return 
	 * El id del carrito 
	 */
	public long getId_carrito();

	/** 
	 * @return El cliente actual del carrito, 0 si no tiene
	 */
	public long getDecliente();

	/** 
	 * @return el estado del carrito
	 */
	public String getEstado();

	/**
	 * configura el id del carrito
	 * @param idCarrito nuevo id para el carrito
	 */
	public void setId_carrito(long idCarrito);

	/**
	 * configura el id del cliente actual
	 * @param idCliente nuevo id del cliente
	 */
	public void setDecliente(long idCliente);

	/**
	 * confugra el estado actual del carrito
	 * @param newEstado nuevo estado del carrito en: OCUPADO, DISPONIBLE, ABANDONADO
	 */
	public void setEstado( String newEstado);

	/** 
	 * @return Una cadena de caracteres con la informacion b�sica.
	 */
	@Override
	public String toString();

	/**
	 * @return una cadena de caracteres con la informaci�n b�sica
	 * de un carrito
	 */
	public String toStringCompleto();
}