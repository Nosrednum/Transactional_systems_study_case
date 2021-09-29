/**
 * 
 */
package main.java.uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Proveen;

/**
 * @author Sebastian Mujica
 *
 */
public class SQLProveen {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	
	 /** Cadena que representa el tipo de consulta que se va a realizar en 
	  *las sentencias de acceso a la base de datos Se renombra acá para 
	  *facilitar la escritura de las sentencias
	  */
	private static final String SQL = PersistenciaSuperandes.SQL;
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * El manejador de persistencia general de la aplicacion.
	 */
	private PersistenciaSuperandes ps;
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * 
	 */
	public SQLProveen(PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una nueva realcción 
	 * de un proveedor con una sucursal.
	 * @param pm
	 * @param idProveedor
	 * @param idSucursal
	 * @return
	 */
	public long adicionarProveen(PersistenceManager pm, long idProveedor, long idSucursal ){
		Query q = pm.newQuery(SQL, "INSERT INTO" + ps.darTablaProveen() + "(idProveedor, idSucursal) values(?,?)"  );
		q.setParameters(idProveedor, idSucursal);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejectura una sentencia SQL y elimina la relación existente entre
	 * un proveedor y una sucursal.
	 * @param pm
	 * @param idProveedor
	 * @param idSucursal
	 * @return
	 */
	public long eliminarProveen(PersistenceManager pm, long idProveedor, long idSucursal){
		Query q = pm.newQuery(SQL, "DELETE FROM" + ps.darTablaProveen() + "WHERE idProveedor = ? AND idSucursal = ?" );
		q.setParameters(idProveedor, idSucursal);
		return (long) q.executeUnique();
	}
	

	/**
	 * Crea y ejecuta una sentencia SQL que genera una lista con las relaciones
	 * existentes en toda la tienda de los proveedores y las sucursales.
	 * @param pm
	 * @return
	 */
	public List<Proveen> darProveen (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProveen ());
		q.setResultClass(Proveen.class);
		List<Proveen> resp = (List<Proveen>) q.execute();
		return resp;
	}
	
	
	public List<long[]> darProveedorProducto(PersistenceManager pm, long idProducto, long idSucursal){
		String sql= " SELECT a.idproveedor , a.precioprodprovee" ;
		sql += " FROM " + ps.darTablaProductoProveedor() + " a, ";
		sql += ps.darTablaProveen() + " b, ";
		sql += "WHERE a.idproducto= ? "; 
		sql += "AND b.idsucursal = ? ";
		sql += "AND b.idproveedor = a.idproveedor ";
		Query q = pm.newQuery(SQL, sql);
		List<long []> respuesta = new LinkedList<long []> ();
		List<Object[]> lista=  q.executeList();
		for ( Object [] tupla : lista)
		{
    			long [] datosResp = new long [2];
    			
    			//id del producto
    			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
    			//cantidad de productos
    			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
    			respuesta.add (datosResp);
		}
		return respuesta;
	}

}
