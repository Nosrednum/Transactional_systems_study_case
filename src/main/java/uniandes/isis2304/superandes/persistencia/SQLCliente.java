package main.java.uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.AlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.Cliente;

class SQLCliente  
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLCliente (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Cliente a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador de la Cliente
	 * @param nombre - El nombre de la Cliente
	 * @param idTipoCliente - El identificador del tipo de Cliente de la Cliente
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarCliente (PersistenceManager pm, long idCliente, String nombre, int puntos, String email, String tipo, String direccion, long identificacion) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente () + "(NOMBRE, PUNTOS, EMAIL, TIPO, DIRECCION, IDENTIFICACION) values (?, ?, ?, ?, ?, ?)");
		q.setParameters(nombre, puntos, email, tipo, direccion, idCliente);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ClienteS de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCliente - El nombre de la Cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorNombre (PersistenceManager pm, String nombreCliente)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE nombre = ?");
		q.setParameters(nombreCliente);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ClienteS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador de la Cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorId (PersistenceManager pm, long idCliente)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE IDENTIFICACION = ?");
		q.setParameters(idCliente);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA Cliente de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador de la Cliente
	 * @return El objeto Cliente que tiene el identificador dado
	 */
	public Cliente darClientePorId (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE IDENTIFICACION = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(idCliente);
		return (Cliente) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de ClienteS de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCliente - El nombre de la Cliente
	 * @return Una lista de objetos Cliente que tienen el nombre dado
	 */
	public List<Cliente> darClientesPorNombre (PersistenceManager pm, String nombreCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE nombre = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(nombreCliente);
		return (List<Cliente>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ClienteS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Cliente
	 */
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente ());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}

	public Cliente getElement(PersistenceManager pm, String condiciones) {
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ pp.darTablaCliente() +" WHERE "+condiciones);
		q.setResultClass(Cliente.class);
		return (Cliente) q.executeUnique();
	}
	
	public List<Long> darClientesSucursal(PersistenceManager pm, long idSucursal){
		String sql = "SELECT DISTINCT a.identificacion";
		sql+= " FROM ";
		sql+= pp.darTablaCliente()+ " a, " +  pp.darTablaFactura()+ " b ";
		sql+= "WHERE";
		sql+= " a.identificacion = b.decliente AND b.desucursal = ? ";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal);
		List<Object> x = q.executeList();
		List<Long> rta = new LinkedList<Long>();
		for (Object object : x) {
			Long y = ( (BigDecimal) object).longValue();
			rta.add(y);
		}
		return rta;
	}
	
	
	public List<Long> darClientesFactura(PersistenceManager pm, long idSucursal, Timestamp fechaInicio, Timestamp fechaFin, long idCliente){
		String sql = "SELECT b.numerofactura ";
		sql += "FROM " + pp.darTablaCliente() +" a, "+  pp.darTablaFactura()+ " b";
		sql+= " WHERE ";
		sql+= "a.identificacion = b.decliente ";
		sql+= "AND b.desucursal = ? ";
		sql+= "AND b.fechafactura >= ? ";
		sql+= "AND b.fechafactura<= ? ";
		sql+= "AND a.identificacion= ?";
		Query q= pm.newQuery(SQL, sql);
		q.setParameters(idSucursal, fechaInicio, fechaFin, idCliente);
		List<Long> rta = new LinkedList<Long>();
		List<Object> x=  (List<Object>) q.executeList();
		for (Object tupla : x) {
			Long datosResp = ((BigDecimal) tupla).longValue ();
			rta.add(datosResp);
		}
		if(rta.isEmpty()){
			return new LinkedList<Long>();
		}
		return rta;
	}
}
