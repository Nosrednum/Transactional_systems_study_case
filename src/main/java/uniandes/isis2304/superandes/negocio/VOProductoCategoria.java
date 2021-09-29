package main.java.uniandes.isis2304.superandes.negocio;

/**
 * 
 * @author Sebastian Mujica
 *
 */
public interface VOProductoCategoria {
	
	/**
	 * @return el identificador del producto de la relacion
	 */
	public long getProducto();
	
	/**
	 * @return el identificador de la categoria de la realación.
	 */
	public long getCategoria();
	
	
	/**
	 * @return Una cadena de caracteres con la información básica
	 */
	@Override
	public String toString();

}
