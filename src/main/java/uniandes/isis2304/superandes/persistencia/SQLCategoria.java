package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.AlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.Categoria;

class SQLCategoria  
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
	public SQLCategoria (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Categoria a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idCategoria - El identificador de la Categoria
	 * @param nombre - El nombre de la Categoria
	 * @param idTipoCategoria - El identificador del tipo de Categoria de la Categoria
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarCategoria (PersistenceManager pm, long idCategoria, String categoria) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCategoria () + "(id_categoria, categoria) values (?, ?)");
		q.setParameters(idCategoria, categoria);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar CategoriaS de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCategoria - El nombre de la Categoria
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCategoriaPorNombre (PersistenceManager pm, String nombreCategoria)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCategoria () + " WHERE categoria = ?");
		q.setParameters(nombreCategoria);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar CategoriaS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCategoria - El identificador de la Categoria
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCategoriaPorId (PersistenceManager pm, long idCategoria)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCategoria () + " WHERE id_categoria = ?");
		q.setParameters(idCategoria);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA Categoria de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCategoria - El identificador de la Categoria
	 * @return El objeto Categoria que tiene el identificador dado
	 */
	public Categoria darCategoriaPorId (PersistenceManager pm, long idCategoria) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCategoria () + " WHERE id_categoria = ?");
		q.setResultClass(Categoria.class);
		q.setParameters(idCategoria);
		return (Categoria) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de CategoriaS de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCategoria - El nombre de la Categoria
	 * @return Una lista de objetos Categoria que tienen el nombre dado
	 */
	public List<Categoria> darCategoriasPorNombre (PersistenceManager pm, String nombreCategoria) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCategoria () + " WHERE categoria = ?");
		q.setResultClass(Categoria.class);
		q.setParameters(nombreCategoria);
		return (List<Categoria>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS CategoriaS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Categoria
	 */
	public List<Categoria> darCategorias (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCategoria ());
		q.setResultClass(Categoria.class);
		return (List<Categoria>) q.executeList();
	}

	public Categoria getElement(PersistenceManager pm, String condiciones) {
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ pp.darTablaCategoria() +" WHERE "+condiciones);
		q.setResultClass(Categoria.class);
		return (Categoria) q.executeUnique();
	}
}
