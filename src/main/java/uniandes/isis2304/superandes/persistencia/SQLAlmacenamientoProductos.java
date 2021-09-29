package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Almacenamiento;
import main.java.uniandes.isis2304.superandes.negocio.AlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.Carrito;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto Almacenamiento de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLAlmacenamientoProductos 
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
	public SQLAlmacenamientoProductos (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN AlmacenamientoProductos de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del AlmacenamientoProductos
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarAlmacenamientoPorId (PersistenceManager pm, long idAlmacenamiento)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamiento () + " WHERE id = ?");
		q.setParameters(idAlmacenamiento);
		return (long) q.executeUnique();
	}

	/** Crea y ejecuta la sentencia SQL para eliminar UN AlmacenamientoProductos de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del AlmacenamientoProductos
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoDeAlmacenamiento (PersistenceManager pm, long idAlmacenamiento)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamientoProductos() + " WHERE id = ?");
		q.setParameters(idAlmacenamiento);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un AlmacenamientoProductos a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del Almacenamiento
	 * @param idProducto - El id del producto
	 * @param idCantidadProductos - La cantidad de productos en el almacenamiento
	 * @return El número de tuplas insertadas
	 */
	public long aniadirProductoDeAlmacenamiento (PersistenceManager pm, long idAlmacenamiento, long idProducto, long cantidad)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAlmacenamientoProductos() + "(IDPRODUCTO, IDALMACENAMIENTO, CANTIDADPRODUCTOS) values (?, ?, ?)");
		q.setParameters(idProducto, idAlmacenamiento, cantidad);
		return (long) q.executeUnique();
	}

	/**
	 *  Actualiza la información de un producto en un almacenamiento dado
	 * @param pm administrador de la persistencia
	 * @param idAlmacenamiento almacenamiento al cual se le hará la operación
	 * @param idProducto producto del almacenamiento sobre el cual se realiza la operación
	 * @param cantidad cantidad de productos a añadir o a eliminar
	 * @param operacion {@code true} si el objetivo es añadir productos {@code false} en caso de lo contrario
	 * @return el numero de tuplas actualizadas
	 */
	@SuppressWarnings("resource")
	public AlmacenamientoProductos actualizarProductoAlmacenamiento(PersistenceManager pm, long idAlmacenamiento, long idProducto, int cantidad, boolean operacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlmacenamientoProductos () + " WHERE IDALMACENAMIENTO = "+idAlmacenamiento+" AND idproducto = ?");
		q.setResultClass(AlmacenamientoProductos.class);
		q.setParameters(idProducto);
		AlmacenamientoProductos toRet =  (AlmacenamientoProductos) q.executeUnique();
		if(operacion) {
			q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlmacenamientoProductos()+ " SET CANTIDADPRODUCTOS = (CANTIDADPRODUCTOS + ?)"
					+ " WHERE idalmacenamiento = ? AND idproducto = ? ");
			q.setParameters(cantidad, idAlmacenamiento, idProducto);
			q.executeUnique();
		}else{
			q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlmacenamientoProductos()+ " SET cantidadproductos = (cantidadproductos - ?)"
					+ " WHERE idalmacenamiento = ? AND idproducto = ? ");
			q.setParameters(cantidad, idAlmacenamiento, idProducto);
			q.executeUnique();
		}
		return toRet;
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Almacenamientos de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Almacenamiento
	 */
	public List<Almacenamiento> darAlmacenamientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlmacenamiento ());
		q.setResultClass(Almacenamiento.class);
		return (List<Almacenamiento>) q.executeList();
	}

	public AlmacenamientoProductos getElement(PersistenceManager pm, String condiciones) {
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ pp.darTablaAlmacenamientoProductos() +" WHERE "+condiciones);
		q.setResultClass(AlmacenamientoProductos.class);
		return (AlmacenamientoProductos) q.executeUnique();
	}

	public List<AlmacenamientoProductos> getElements(PersistenceManager pm, String condiciones) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlmacenamientoProductos () + " WHERE "+condiciones);
		q.setResultClass(AlmacenamientoProductos.class);
		return (List<AlmacenamientoProductos>) q.executeList();
	}

	public AlmacenamientoProductos getElementCompleteSentence(PersistenceManager pm, String sentenciaSql) {
		Query q = pm.newQuery(SQL, sentenciaSql);
		q.setResultClass(AlmacenamientoProductos.class);
		return (AlmacenamientoProductos) q.executeUnique();
	}

	public List getElemetsCompleteSentence(PersistenceManager pm, String condiciones) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void restarProductosEstanteBodega(PersistenceManager pm, long idAlmacenamiento, int cantidadProductos){
		String sql = "UPDATE " + pp.darTablaAlmacenamientoProductos()+ " SET CANTIDADPRODUCTOS = (CANTIDADPRODUCTOS - ?)";
		sql += " WHERE idalmacenamiento = ? ";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(cantidadProductos , idAlmacenamiento);
		q.executeUnique();
	}
	
	public void sumarProductosEstanteBodega(PersistenceManager pm, long idAlmacenamiento, int cantidadProductos){
		String sql = "UPDATE " + pp.darTablaAlmacenamientoProductos()+ " SET CANTIDADPRODUCTOS = (CANTIDADPRODUCTOS + ?)";
		sql += " WHERE idalmacenamiento = ? ";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(cantidadProductos , idAlmacenamiento);
		q.executeUnique();
	}


	public long deleteElements(PersistenceManager pm, String condiciones) {
		Query<?> q = pm.newQuery(SQL, "DELETE FROM "+ pp.darTablaAlmacenamientoProductos() +" WHERE "+condiciones);
		return (long) q.executeUnique();
	}
}
