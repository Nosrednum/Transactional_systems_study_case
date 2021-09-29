package main.java.uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Pedido;

class SQLPedido  
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
	public SQLPedido (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Pedido a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador de la Pedido
	 * @param nombre - El nombre de la Pedido
	 * @param idTipoPedido - El identificador del tipo de Pedido de la Pedido
	 * @param gradoAlcohol - El grado de alcohol de la Pedido (Mayor que 0)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPedido (PersistenceManager pm, long idPedido, String fechaEntrega,
			double evaluacionCalidad, double evaluacionCantidad, double precioPedido, String fechaAcordada, long proveedor, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPedido () + "(ID_PEDIDO, FECHA_ENTREGA,"
				+ " EVCANT, EVCAL, PRECIO_PEDIDO, FECHA_ACORDADA, PROVEEDOR, SUCURSAL) values (?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(idPedido, fechaEntrega, evaluacionCantidad, evaluacionCalidad, precioPedido, fechaAcordada,proveedor, sucursal);
		return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Pedido a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador de la Pedido
	 * @param nombre - El nombre de la Pedido
	 * @param idTipoPedido - El identificador del tipo de Pedido de la Pedido
	 * @param gradoAlcohol - El grado de alcohol de la Pedido (Mayor que 0)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPedido (PersistenceManager pm, long idPedido, String fechaEntrega,
			double evaluacionCalidad, double evaluacionCantidad, double precioPedido, Timestamp fechaAcordada, long proveedor, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPedido () + " (ID_PEDIDO, FECHA_ENTREGA,"
				+ " EVCANT, EVCAL, PRECIO_PEDIDO, FECHA_ACORDADA, PROVEEDOR, SUCURSAL) values (?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(idPedido, fechaEntrega, evaluacionCantidad, evaluacionCalidad, precioPedido, fechaAcordada,proveedor, sucursal);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PedidoS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador de la Pedido
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPedidoPorId (PersistenceManager pm, long idPedido)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido () + " WHERE id_Pedido = ?");
		q.setParameters(idPedido);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA Pedido de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador de la Pedido
	 * @return El objeto Pedido que tiene el identificador dado
	 */
	public Pedido darPedidoPorId (PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido () + " WHERE id_Pedido = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(idPedido);
		return (Pedido) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS PedidoS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Pedido
	 */
	public List<Pedido> darPedidos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido ());
		q.setResultClass(Pedido.class);
		return (List<Pedido>) q.executeList();
	}
}
