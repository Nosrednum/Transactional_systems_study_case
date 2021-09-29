package main.java.uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de <b>SUCURSAL</b>.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Anderson Barragán
 */
public interface VOSucursal {

	/* ****************************************************************
	 * 			Métodos 
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
	 *  @return el tamaño de la sucursal 
	 */
	public double getTamanio();


	/**
	 *  @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();
}
