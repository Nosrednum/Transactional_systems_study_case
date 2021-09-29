/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

/**
 * @author Sebastian Mujica
 *
 */
public class Proveedor implements VOProveedor{
	
	/**
	 * Nit del proveedor
	 */
	private long nit;
	
	/**
	 * nombre del proveedor
	 */
	private String nombre;
	
	
	
	/**
	 * Constructor vacío
	 */
	public Proveedor(){
		this.nit=0;
		this.nombre="";
	}
	
	/**
	 * Constructor usando los parámetros.
	 * @param nit
	 * @param nombre
	 */
	public Proveedor(long nit, String nombre) {
		this.nit = nit;
		this.nombre = nombre;
	}

	
	

	@Override
	public long getNit() {
		return this.nit;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}



	/**
	 * @param nit the nit to set
	 */
	public void setNit(long nit) {
		this.nit = nit;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Proveedor [nit=" + this.nit + ", nombre=" + this.nombre + "]";
	}

}
