package main.java.uniandes.isis2304.superandes.negocio;


/**
 * Interfaz para los métodos get de <b>SUCURSAL</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Sebastian Mujica
 */
public interface VOFactura {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	/**
	 * @return el número de la factura.
	 */
	public long getNumeroFactura();

	/**
	 * @return  la fecha en que se realizó la factura.
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
	 * @return Una cadena de caracteres con la información básica.
	 * */
	@Override
	public String toString();
}
