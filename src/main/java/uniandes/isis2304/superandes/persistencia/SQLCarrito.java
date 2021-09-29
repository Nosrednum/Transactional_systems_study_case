package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.AlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.Carrito;
import main.java.uniandes.isis2304.superandes.negocio.Producto;
import oracle.net.aso.s;


/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto Carrito de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Anderson Barragán
 */
public class SQLCarrito {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;

	private static final String SERIALIZABLE = "SET CURRENT ISOLATION = SERIALIZABLE ", REPEATABLEREAD = "SET CURRENT ISOLATION = REPEATABLE READ", READCOMMITED = "READ COMMITED";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLCarrito (PersistenciaSuperandes pp)
	{
		this.ps = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Carrito a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador 
	 * @param nombre - El nombre de la Cliente
	 * @param idTipoCliente - El identificador del tipo de Cliente de la Cliente
	 * @return EL número de tuplas insertadas
	 */
	public long aniadirCarrito(PersistenceManager pm, long idCarrito) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCarrito() + "(ID_CARRITO, ESTADO, DECLIENTE) values (?, ?, ?)");
		q.setParameters(idCarrito,"DISPONIBLE",0);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ClienteS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCarrito - El identificador de la Cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCarritoPorId (PersistenceManager pm, long idCarrito)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCarrito() + " WHERE IDENTIFICACION = ?");
		q.setParameters(idCarrito);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CARRITO de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @return El objeto Carrito que tiene el identificador dado
	 */
	public Carrito darCarritoPorId (PersistenceManager pm, long idCarrito) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarrito() + " WHERE IDENTIFICACION = ?");
		q.setResultClass(Carrito.class);
		q.setParameters(idCarrito);
		return (Carrito) q.executeUnique();
	}



	public long adicionarProductoACarrito(PersistenceManager pm, long idCarrito, long idProducto, int cantidad) {
		Query<?> q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaAlmacenamientoProductos() + "(IDPRODUCTO, IDALMACENAMIENTO, CANTIDADPRODUCTOS) values (?, ?, ?)");
		q.setParameters(idProducto,idCarrito,cantidad);
		return (long) q.executeUnique();
	}

	public List<Object[]> listarProductosDeCarrito(PersistenceManager pm, long idCarrito) {
		Query q = pm.newQuery(SQL, "SELECT "+ " idproducto"+ ","+ " cantidadproductos" + " FROM " + ps.darTablaAlmacenamientoProductos()+ " WHERE IDALMACENAMIENTO = ?");
		q.setParameters(idCarrito);
		return (List<Object[]>) q.executeList();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los carritos disponibles de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Almacenamiento
	 */
	@SuppressWarnings("unchecked")
	public List<Carrito> darCarritosDisponibles (PersistenceManager pm, long sucursal)
	{
		Query q = pm.newQuery(SQL, "SELECT ID_CARRITO,ESTADO,DECLIENTE FROM (" + ps.darTablaAlmacenamiento() + " INNER JOIN "
				+ ps.darTablaCarrito() +" ON ID = ID_CARRITO) WHERE ESTADO IN (?, ?) AND SUCURSAL = ?");
		q.setParameters("DISPONIBLE", "ABANDONADO", sucursal);
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}
	public List<Carrito> darCarritosEstado (PersistenceManager pm, long sucursal,String estado)
	{
		Query q = pm.newQuery(SQL, "SELECT ID_CARRITO,ESTADO,DECLIENTE FROM (" + ps.darTablaAlmacenamiento() + " INNER JOIN "
				+ ps.darTablaCarrito() +" ON ID = ID_CARRITO) WHERE ESTADO IN (?, ?) AND SUCURSAL = ?");
		q.setParameters("DISPONIBLE", "ABANDONADO", sucursal);
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}

	public List<Producto> recolectarProductosDeCarrito(PersistenceManager pm, long idCarrito) {
		Query<?> q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaAlmacenamientoProductos() + " WHERE IDALMACENAMIENTO = (?)");
		q.setParameters(idCarrito);
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}

	public Carrito getElement(PersistenceManager pm, String condiciones) {
		Query<?> q = pm.newQuery(SQL, "SELECT * FROM "+ ps.darTablaCarrito() +" WHERE "+condiciones);
		q.setResultClass(Carrito.class);
		return (Carrito) q.executeUnique();
	}

	public long tomarCarrito(PersistenceManager pm, long idCarrito, long idCliente) {
		Query<?> q = pm.newQuery(SQL,"UPDATE "+ ps.darTablaCarrito() +" SET ESTADO = 'OCUPADO', DECLIENTE = " + idCliente + " WHERE ID_CARRITO = ?");
		q.setParameters(idCarrito);
		return (long) q.executeUnique();
	}


	public long cambiarEstadoCarrito(PersistenceManager pm, long id_carrito, String nuevoEstado) {
		Query<?> q = pm.newQuery(SQL, "UPDATE "+ ps.darTablaCarrito() +" SET ESTADO = '"+nuevoEstado+"', DECLIENTE = " + 0 + " WHERE ID_CARRITO = ?");
		q.setParameters(id_carrito);
		return (long) q.executeUnique();
	}


	public List<Carrito> getElemets(PersistenceManager pm, String condiciones) {
		Query<?> q = pm.newQuery(SQL, "SELECT * FROM "+ ps.darTablaCarrito() +" WHERE "+condiciones);
		q.setResultClass(Carrito.class);
		return (List<Carrito>)q.executeList();
	}


	public Carrito getElementCompleteSentence(PersistenceManager pm, String sentenciaSql) {
		Query<?> q = pm.newQuery(SQL, sentenciaSql);
		q.setResultClass(Carrito.class);
		return (Carrito) q.executeUnique();
	}

	public List<Carrito> getElemetsCompleteSentence(PersistenceManager pm, String sentenciaCompleta) {
		Query<?> q = pm.newQuery(SQL, sentenciaCompleta);
		q.setResultClass(Carrito.class);
		return (List<Carrito>)q.executeList();
	}


	public long deleteElemets(PersistenceManager pm, String condiciones) {
		Query<?> q = pm.newQuery(SQL, "DELETE FROM "+ ps.darTablaCarrito() +" WHERE "+condiciones);
		return (long)q.executeUnique();
	}
}
