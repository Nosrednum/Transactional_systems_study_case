package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Producto;

class SQLProducto  
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
	public SQLProducto (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Producto a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador de la Producto
	 * @param nombre - El nombre de la Producto
	 * @param idTipoProducto - El identificador del Producto de la Producto
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarProducto (PersistenceManager pm, long idProducto, String nombre, String marca, String presentacion,
			String unidadMedida, String empacado, double cantPresentacion, double volumenEmpaque, double peso, String fechavencimiento) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCategoria () + "(NOMBRE, MARCA, PRESENTACION, EMPACADO, CANTIDADPRESENTACION"
				+ "VOLUMENEMPAQUE, PESOEMPAQUE, FECHADEVENCIMIENTO, CODIGOBARRAS, UNIDADMEDIDA) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(nombre, marca, presentacion, empacado, cantPresentacion, volumenEmpaque, peso, fechavencimiento,idProducto , unidadMedida);
		return (long) q.executeUnique();           
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ProductoS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador de la Producto
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoPorId (PersistenceManager pm, long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE codigobarras = ?");
		q.setParameters(idProducto);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA Producto de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador de la Producto
	 * @return El objeto Producto que tiene el identificador dado
	 */
	public Producto darProductoPorNombre (PersistenceManager pm, String nombreProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE NOMBRE = ?");
		q.setResultClass(Producto.class);
		q.setParameters(nombreProducto);
		return (Producto) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA Producto de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador de la Producto
	 * @return El objeto Producto que tiene el identificador dado
	 */
	public Producto darProductoPorId (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE codigobarras = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idProducto);
		return (Producto) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de Los Productos de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Producto
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
}
