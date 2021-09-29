package main.java.uniandes.isis2304.superandes.negocio;


/**
 * Interfaz para los m�todos get de <b>SUCURSAL</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Sebastian Mujica
 */
public interface VOFactura {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return el n�mero de la factura.
	 */
	public long getNumeroFactura();

	/**
	 * @return  la fecha en que se realiz� la factura.
	 */
	public String getFechaFactura();

	/**
	 * @return el id del cliente al que pertenece la factura.
	 */
	public long getDeCliente();

	/**
	 * @return el id de la sucursal a la que pertenece la factura.
	 */
	public long getDeSucursal();


	/**
	 * @return Una cadena de caracteres con la informaci�n b�sica.
	 * */
	@Override
	public String toString();
}
