package main.java.uniandes.isis2304.superandes.negocio;

import java.util.List;

/**
 * @author Anderson Barrag�n
 */
public class Cliente implements VOCliente{
	
	/**
	 * identificaci�n del cliente
	 * puede ser c�dula o nit 
	 * basado en el tipo
	 */
	private long identificacion;
	
	/**
	 * Identificador del carro actual del cliente
	 * {@code null} o asociado a un carro de la sucursal
	 */
	private long idCarrito;
	
	/**
	 * nombre del cliente, puede 
	 * ser <b>{@code null}</b> si el cliente no 
	 * esta registrado
	 */
	private String nombre;
	
	/**
	 * puntos del cliente, pueden 
	 * ser null si el cliente
	 * no est� registrado.
	 */
	private int puntos; 
	
	/**
	 * Correo electr�nico, puede ser 
	 * Null si el cliente no esta registrado
	 */
	private String email;
	
	/**
	 * El tipo de cliente, puede ser 
	 * "NR, "NATURAL", "EMPRESA"
	 */
	private String tipo;
	
	/**
	 * La direcci�n del cliente, puede ser
	 * Null si el cliente no esta registrado
	 */
	private String direccion;
	
	/**
	 * Constructor vac�o.
	 */
	public Cliente() {
		this.identificacion	= 0	;
		this.idCarrito 		= 0	;
		this.nombre			= "";
		this.puntos			= 0	;
		this.email			= "";
		this.tipo			= "";
		this.direccion		= "";
	}

	/**
	 * Constructor usando par�metros.
	 * @param identificacion
	 * @param nombre
	 * @param puntos
	 * @param email
	 * @param tipo
	 * @param direccion
	 */
	public Cliente(long identificacion, String nombre, int puntos, String email, String tipo, String direccion) {
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.puntos = puntos;
		this.email 	= email	;
		this.tipo 	= tipo	;
		this.direccion = direccion;
	}

	/**
	 * @param identificacion the identificaci�n to set
	 */
	public void setIdentificacion(long identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param puntos the puntos to set
	 */
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override public long getIdentificacion() {
		return this.identificacion;
	}
	
	@Override public long getCarrito() {
		return this.idCarrito;
	}

	@Override public String getNombre() {
		return this.nombre;
	}

	@Override public int getPuntos() {
		return this.puntos;
	}

	@Override public String getEmail() {
		return this.email;
	}

	@Override public String getTipo() {
		return this.tipo;
	}

	@Override public String getDireccion() {
		return this.direccion;
	}

	@Override public List<Object[]> getFacturas() {
		return null;
	}

	@Override public String toStringCompleto() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cliente [identificacion=" + identificacion + ", nombre=" + nombre + ", puntos=" + puntos + ", email="
				+ email + ", tipo=" + tipo + ", direccion=" + direccion + "]";
	}
	

}
