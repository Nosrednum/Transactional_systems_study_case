package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.ProductosFactura;

class SQLProductosFactura  
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
	public SQLProductosFactura (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ProductosFactura a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idProductosFactura - El identificador de la ProductosFactura
	 * @param nombre - El nombre de la ProductosFactura
	 * @param idTipoProductosFactura - El identificador del tipo de ProductosFactura de la ProductosFactura
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarProductosFactura (PersistenceManager pm,long idFactura, long idProducto, long cantidadFacturada) 
	{  
		Query q = pm.newQuery(SQL, " INSERT INTO " + pp.darTablaProductosFactura() + " values (?, ?, ?)");
		q.setParameters(idFactura, idProducto, cantidadFacturada);
        long x=  (long) q.executeUnique();
        return x;
	}
	

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ProductosFactura de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductosFactura - El identificador de la ProductosFactura
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductosFacturaPorId (PersistenceManager pm, long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductosFactura () + " WHERE producto = ?");
		q.setParameters(idProducto);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ProductosFactura de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductosFactura - El identificador de la ProductosFactura
	 * @return El objeto ProductosFactura que tiene el identificador dado
	 */
	public ProductosFactura darProductosFacturaPorId (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductosFactura () + " WHERE Producto = ?");
		q.setResultClass(ProductosFactura.class);
		q.setParameters(idProducto);
		return (ProductosFactura) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ProductosFacturaS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ProductosFactura
	 */
	public List<ProductosFactura> darProductosFacturas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductosFactura ());
		q.setResultClass(ProductosFactura.class);
		return (List<ProductosFactura>) q.executeList();
	}
}
