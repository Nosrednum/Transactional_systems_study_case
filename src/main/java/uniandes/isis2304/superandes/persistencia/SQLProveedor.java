/**
 * 
 */
package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Proveedor;
import main.java.uniandes.isis2304.superandes.negocio.Proveen;
import main.java.uniandes.isis2304.superandes.negocio.Sucursal;

/**
 * @author Sebastian Mujica
 *
 */
public class SQLProveedor {


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
	public SQLProveedor(PersistenciaSuperandes ps) {
		this.ps = ps;
	}


	/**
	 * Crea y ejectura una sentencia para adicionar un nuevo proveedor
	 * a la base de datos de super andes.
	 * @param pm putamierda
	 * @param nit identificador único de un proveedor
	 * @param nombre 
	 * @return
	 */
	public long adicionarProveedor(PersistenceManager pm, long nit, String nombre){
		Query q = pm.newQuery(SQL, "INSERT INTO" + ps.darTablaProveedor() + "(nit, nombre) values(?,?)"  );
		q.setParameters(nit, nombre);
		return (long) q.executeUnique();
	}


	/**
	 * Crea y jectura una setnencia para eliminar un provedor de la base de
	 * datos de superAndes a través de su nit.
	 * @param pm
	 * @param nit identificador único de un proveedor
	 * @return
	 */
	public long eliminarProveedorPorNit(PersistenceManager pm, long nit){
		Query q = pm.newQuery(SQL, "DELETE FROM" + ps.darTablaProveedor() + "WHERE nit = ?");
		q.setParameters(nit);
		return (long) q.executeUnique();
	}


	/**
	 * Crea y ejectura una sentencia que permite encontrar un proveedor
	 * por su nit.
	 * @param pm
	 * @param nit identificador único de un proveedor
	 * @return
	 */
	public Sucursal darProveedorPorId(PersistenceManager pm, long nit){
		Query q = pm.newQuery(SQL, "SELECT FROM" + ps.darTablaProveedor() + "WHERE nit = ?");
		q.setResultClass(Proveedor.class);
		q.setParameters(nit);
		return (Sucursal) q.executeUnique();
	}

	/**
	 * Crea y ejectura una sentencia sql que permite encontrar una lista con todos
	 * los proveedores existente en la tienda.
	 * @param pm
	 * @return
	 */
	public List<Proveedor> darProveedores(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProveedor ());
		q.setResultClass(Proveedor.class);
		List<Proveedor> resp = (List<Proveedor>) q.execute();
		return resp;
	}


}
