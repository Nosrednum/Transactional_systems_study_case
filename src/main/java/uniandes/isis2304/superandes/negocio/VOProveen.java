package main.java.uniandes.isis2304.superandes.negocio;

public interface VOProveen {
	
	/**
	 * @return El identificador del proveedor
	 * que tiene la relaci�n con la sucursal
	 */
	public long getIdProveedor();
	
	/**
	 * @return El identificador de la sucursal a la cual 
	 * que tiene la relaci�n con el proveedor
	 */
	public long getIdSucursal();
	
	
	/** 
	 * @return Una cadena de caracteres con la informaci�n b�sica.
	 */
	@Override
	public String toString();

}
