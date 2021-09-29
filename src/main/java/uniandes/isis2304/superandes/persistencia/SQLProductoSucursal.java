package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.ProductoSucursal;

class SQLProductoSucursal  
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
	public SQLProductoSucursal (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ProductoSucursal a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idProductoSucursal - El identificador de la ProductoSucursal
	 * @param nombre - El nombre de la ProductoSucursal
	 * @param idTipoProductoSucursal - El identificador del tipo de ProductoSucursal de la ProductoSucursal
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarProductoSucursal (PersistenceManager pm, long Sucursal, long producto, int nivelReorden, double precioUnitario, double precioSucursal,double precioUnidadMedida) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProductoSucursal () + "(SUCURSAL, PRODUCTO, NIVREORDEN, PRECIO_UNITARIO, PRECIO_SUCURSAL, PRECIO_UNIDAD_MEDIDA) values (?, ?, ?, ?, ?, ?)");
		q.setParameters(Sucursal, producto,nivelReorden, precioUnitario, precioSucursal, precioUnidadMedida);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ProductoSucursal de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductoSucursal - El identificador de la ProductoSucursal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductosSucursalPorId (PersistenceManager pm, long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoSucursal () + " WHERE producto = ?");
		q.setParameters(idProducto);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ProductoSucursal de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProductoSucursal - El identificador de la ProductoSucursal
	 * @return El objeto ProductoSucursal que tiene el identificador dado
	 */
	public ProductoSucursal darProductoSucursalPorId (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductoSucursal () + " WHERE Producto = ?");
		q.setResultClass(ProductoSucursal.class);
		q.setParameters(idProducto);
		return (ProductoSucursal) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS ProductoSucursalS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ProductoSucursal
	 */
	public List<ProductoSucursal> darProductoSucursals (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProductoSucursal ());
		q.setResultClass(ProductoSucursal.class);
		return (List<ProductoSucursal>) q.executeList();
	}
	
	public long nivelDeReorden(PersistenceManager pm, long idSucursal, long codigoBarras){
		String sql = "SELECT nivreorden FROM " + pp.darTablaProductoSucursal ();
		sql+= " WHERE ";
		sql+= "sucursal = ?";
		sql+= " AND producto = ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal, codigoBarras);
		return (long) q.executeResultUnique();
	}
}
