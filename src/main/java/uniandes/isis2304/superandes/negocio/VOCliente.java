package main.java.uniandes.isis2304.superandes.negocio;

import java.util.List;

/**
 * Interfaz para los m�todos get de <b>CLIENTE</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOCliente {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return la identificaci�n del cliente.
	 */
	public long getIdentificacion();
	
	/**
	 * @return la identificaci�n del carro asociado al cliente.
	 */
	public long getCarrito();
	
	/**
	 * @return el nombre del cliente. Puede ser null en caso 
	 * de no ser un cliente registrado
	 */
	public String getNombre();
	
	/**
	 * @return los puntos del cliente. puede ser null
	 * en caso de no ser un cliente registrado.
	 */
	public int getPuntos();
	
	/**m
	 * @return Correo electr�nico del cliente, puede ser null
	 * en caso que no sea un cliente registrado.
	 */
	public String getEmail();
	
	/**
	 * @return el tipo de cliente.
	 */
	public String getTipo();
	
	/**
	 * @return la direcci�n del cliente. Puede ser null en caso 
	 * que no sea un cliente registrado
	 */
	public String getDireccion();
	
	/**
	 * @return Todas las facturas que tiene un cliente en
	 * la sucursal.
	 */
	public List<Object []> getFacturas();
	


	/**
	 *  @return Una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
	
	/**
	 * @return una cadena de caracteres con la informaci�n b�sica
	 * de un cliente, adem�s contiene la lista de todas las facturas
	 * que tiene en la SUCURSAL( una por l�nea)
	 */
	public String toStringCompleto();
}
