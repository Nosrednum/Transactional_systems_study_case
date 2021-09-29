package main.java.uniandes.isis2304.superandes.negocio;

public interface VOProveen {
	
	/**
	 * @return El identificador del proveedor
	 * que tiene la relación con la sucursal
	 */
	public long getIdProveedor();
	
	/**
	 * @return El identificador de la sucursal a la cual 
	 * que tiene la relación con el proveedor
	 */
	public long getIdSucursal();
	
	
	/** 
	 * @return Una cadena de caracteres con la información básica.
	 */
	@Override
	public String toString();

}
