package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.ProductoCategoria;

class SQLProductoCategoria  
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
	public SQLProductoCategoria (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ProductoCategoria a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idProductoCategoria - El identificador de la ProductoCategoria
	 * @param nombre - El nombre de la ProductoCategoria
	 * @param idTipoProductoCategoria - El identificador del tipo de ProductoCategoria de la ProductoCategoria
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarProductoCategoria (PersistenceManager pm, long idProductoCategoria, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProductoCategoria () + "(PRODUCTO, categoria) values (?, ?)");
		q.setParameters(idProducto, idProductoCategoria);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ProductoCategoriaS de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProductoCategoria - El nombre de la ProductoCategoria
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoCategoriaPorCategoria (PersistenceManager pm, long categoria)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoCategoria () + " WHERE Categoria = ?");
		q.setParameters(categoria);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ProductoCategoriaS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductoCategoria - El identificador de la ProductoCategoria
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoCategoriaPorProducto (PersistenceManager pm, long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoCategoria () + " WHERE producto = ?");
		q.setParameters(idProducto);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ProductoCategoria de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductoCategoria - El identificador de la ProductoCategoria
	 * @return El objeto ProductoCategoria que tiene el identificador dado
	 */
	public ProductoCategoria darProductoCategoriaPorProducto (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductoCategoria () + " WHERE producto = ?");
		q.setResultClass(ProductoCategoria.class);
		q.setParameters(idProducto);
		return (ProductoCategoria) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de ProductoCategoriaS de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param Categoria - El nombre de la ProductoCategoria
	 * @return Una lista de objetos ProductoCategoria que tienen el nombre dado
	 */
	public List<ProductoCategoria> darProductoCategoriasPorCategoria (PersistenceManager pm, long Categoria) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductoCategoria () + " WHERE categoria = ?");
		q.setResultClass(ProductoCategoria.class);
		q.setParameters(Categoria);
		return (List<ProductoCategoria>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ProductoCategoriaS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ProductoCategoria
	 */
	public List<ProductoCategoria> darProductoCategorias (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductoCategoria ());
		q.setResultClass(ProductoCategoria.class);
		return (List<ProductoCategoria>) q.executeList();
	}
}
