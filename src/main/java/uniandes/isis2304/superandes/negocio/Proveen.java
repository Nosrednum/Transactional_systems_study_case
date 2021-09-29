package main.java.uniandes.isis2304.superandes.negocio;

/**
 * 
 * @author Sebastian Mujica
 *
 */
public class Proveen implements VOProveen{
	
	/**
	 * el id del proveedor que provee a la sucursal
	 */
	private long idProveedor;
	
	/**
	 * el id de la sucursal que es proveida.
	 */
	private long idSucursal;
	
	/**
	 * Constructor vacío.
	 */
	public Proveen() {
		this.idProveedor=0;
		this.idSucursal=0;
	}

	/**
	 * Constructor con parámetros
	 * @param idProveedor
	 * @param idSucursal
	 */
	public Proveen(long idProveedor, long idSucursal) {
		this.idProveedor = idProveedor;
		this.idSucursal = idSucursal;
	}

	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	@Override
	public long getIdProveedor() {
		return this.idProveedor;
	}

	@Override
	public long getIdSucursal() {
		return this.idSucursal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Proveen [idProveedor=" + idProveedor + ", idSucursal=" + idSucursal + "]";
	}
	
	
	

}
