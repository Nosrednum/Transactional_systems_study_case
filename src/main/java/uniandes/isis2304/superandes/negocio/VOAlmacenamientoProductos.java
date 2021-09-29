/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public interface VOAlmacenamientoProductos {

	/**
	 * @return el id del producto en el almacenamiento
	 * ya sea estante o bodega.
	 */
	public long getIdProducto();
	
	/**
	 * @return el id del almacenamiento que puede ser
	 * un estatne o una bodega.
	 */
	public long getIdAlmacenamiento();
	
	/**
	 * @return la cantidad de productos existente 
	 * en el almacenamiento determinado, siendo este un
	 * almacen o una bodega.
	 */
	public int cantidadProductos();
	
	/** 
	 * @return Una cadena de caracteres con la informacion basica.
	 */
	@Override
	public String toString();
}
