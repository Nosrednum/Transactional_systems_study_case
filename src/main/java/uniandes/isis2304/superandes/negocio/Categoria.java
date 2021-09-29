/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sebastian Mujica
 *
 */
public class Categoria implements VOCategoria{
	
	private long idCategoria;
	
	private String categoria;
	
	private List< Object[]> productosCategoria;
	
	/**
	 * Constructor vacío.
	 */
	public Categoria() {
		this.idCategoria=0;
		this.categoria="";
		
		this.productosCategoria = new LinkedList<Object []>();
	}

	/**
	 * Constructor con parámetros.
	 * @param idCategoria
	 * @param categoría
	 */
	public Categoria(long idCategoria, String categoria) {
		this.idCategoria = idCategoria;
		this.categoria = categoria;
		
		//Estos valores no se concoen en el momento de la construcción de la categoria
		this.productosCategoria = new LinkedList<Object []>();

	}

	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public long getId() {
		return this.idCategoria;
	}

	@Override
	public String getCategoria() {
		return this.categoria;
	}

	@Override
	public List<Object[]> getProductosCategoria() {
		return this.productosCategoria;
	}

	/**s
	 * @param productosCategoria the productosCategoria to set
	 */
	public void setProductosCategoria(List<Object[]> productosCategoria) {
		this.productosCategoria = productosCategoria;
	}

	
	//MÉTODO NO SE COMPLETA, NO ES UN REQUERIMIENTO FUNCIONAL.
	@Override
	public String toStringCompleto() {
		String resp = this.toString();
		resp+= "\n --- Producos categoría \n";
		int i = 1;
		for (Object[] objects : productosCategoria) {
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", categoria=" + categoria + "]";
	}
	
	
	

}
