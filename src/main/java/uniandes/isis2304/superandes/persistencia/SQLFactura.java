package main.java.uniandes.isis2304.superandes.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Factura;

class SQLFactura  
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
	public SQLFactura (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Factura a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idFactura - El identificador de la Factura
	 * @param nombre - El nombre de la Factura
	 * @param idTipoFactura - El identificador del tipo de Factura de la Factura
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarFactura (PersistenceManager pm, long idFactura, Timestamp fecha, long deSucursal, long deCliente) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaFactura () + " values (?, ?, ?, ?)");
		q.setParameters(fecha, deSucursal, deCliente, idFactura);
	    long x =  (long) q.executeUnique();     
	    return x;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Factura a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idFactura - El identificador de la Factura
	 * @param nombre - El nombre de la Factura
	 * @param idTipoFactura - El identificador del tipo de Factura de la Factura
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarFactura (PersistenceManager pm, long idFactura, String fecha, long deSucursal, long deCliente) 
	{
		System.out.println("entra Factura" + "--" + fecha);
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaFactura () + "values (?, ?, ?, ?)");
		q.setParameters(fecha, deSucursal, deCliente, idFactura);
	    long x =  (long) q.executeUnique();     
	    System.out.println(x);
	    return x;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar FacturaS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idFactura - El identificador de la Factura
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarFacturaPorId (PersistenceManager pm, long idFactura)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura () + " WHERE numeroFactura = ?");
		q.setParameters(idFactura);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA Factura de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idFactura - El identificador de la Factura
	 * @return El objeto Factura que tiene el identificador dado
	 */
	public Factura darFacturaPorId (PersistenceManager pm, long idFactura) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura () + " WHERE numeroFactura = ?");
		q.setResultClass(Factura.class);
		q.setParameters(idFactura);
		return (Factura) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS FacturaS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Factura
	 */
	public List<Factura> darFacturas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura ());
		q.setResultClass(Factura.class);
		return (List<Factura>) q.executeList();
	}
	
	
	public Timestamp fechaInicioSuperandes(PersistenceManager pm){
		String sql = " SELECT fechafactura";
		sql += " FROM ";
		sql += "(SELECT * FROM "+ pp.darTablaFactura() + " ORDER BY fechafactura ASC)";
		sql += " WHERE ROWNUM <=1 ";
		Query q = pm.newQuery(SQL, sql);
		Timestamp x=  (Timestamp) q.executeUnique();
		return x;
	}
	
	
}
