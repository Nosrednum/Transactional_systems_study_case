package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los m�todos get de <b>Proveedor</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Sebastian Mujica
 */
public interface VOProveedor {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 * @return el NIT del proveedor
	 */
	public long getNit();
	
	/**
	 * @return el nombre del proveedor
	 */
	public String getNombre();

	
	
	/** @return Una cadena de c */
	@Override
	public String toString();
}
