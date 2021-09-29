package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.ProductosPedidos;

class SQLProductosPedidos  
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
	public SQLProductosPedidos (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ProductosPedidos a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idProductosPedidos - El identificador de la ProductosPedidos
	 * @param nombre - El nombre de la ProductosPedidos
	 * @param idTipoProductosPedidos - El identificador del tipo de ProductosPedidos de la ProductosPedidos
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarProductosPedidos (PersistenceManager pm,long idProducto, long idPedido, int cantidadRecompra) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProductosPedidos () + "(IDPRODUCTO, IDPEDIDO, CANTIDADRECOMPRA) values (?, ?, ?)");
		q.setParameters(idProducto, idPedido, cantidadRecompra);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ProductosPedidos de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductosPedidos - El identificador de la ProductosPedidos
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductosPedidosPorId (PersistenceManager pm, long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductosPedidos () + " WHERE idproducto = ?");
		q.setParameters(idProducto);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ProductosPedidos de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductosPedidos - El identificador de la ProductosPedidos
	 * @return El objeto ProductosPedidos que tiene el identificador dado
	 */
	public ProductosPedidos darProductosPedidosPorId (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductosPedidos () + " WHERE idProducto = ?");
		q.setResultClass(ProductosPedidos.class);
		q.setParameters(idProducto);
		return (ProductosPedidos) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ProductosPedidosS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ProductosPedidos
	 */
	public List<ProductosPedidos> darProductosPedidoss (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductosPedidos ());
		q.setResultClass(ProductosPedidos.class);
		return (List<ProductosPedidos>) q.executeList();
	}
}
