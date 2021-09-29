package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los m�todos get de <b>SUCURSAL</b>.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barrag�n
 */
public interface VOSucursal {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/

	/**
	 *  @return El id de la sucursal 
	 */
	public long getId();

	/** 
	 * @return el nombre de la sucursal 
	 */
	public String getNombre();

	/** 
	 * @return la direccion de la sucursal 
	 */
	public String getDireccion();

	/** 
	 * @return la ciudad de la sucursal
	 */
	public String getCiudad();

	/** 
	 * @return El segmento de mercado de la sucursal 
	 */
	public String getSegmentoDeMercado();

	/**
	 *  @return el tama�o de la sucursal 
	 */
	public double getTamanio();


	/**
	 *  @return Una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();
}
