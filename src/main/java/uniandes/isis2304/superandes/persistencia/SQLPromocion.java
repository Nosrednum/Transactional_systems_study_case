/**
 * 
 */
package main.java.uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Promocion;

/**
 * @author Sebastian Mujica
 *
 */
public class SQLPromocion {

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
	 * @param ps
	 */
	public SQLPromocion(PersistenciaSuperandes ps) {
		this.ps = ps;
	}


	/**
	 * Crea y ejecuta una sentencia SQL que crea una promoción en la tienda
	 * @param pm
	 * @param descripcion
	 * @param fechaFin
	 * @param cantidadProductos
	 * @param id
	 * @return
	 */
	public long adicionarPromocion(PersistenceManager pm, String descripcion, String fechaFin, int cantidadProductos, long id ){
		Query q = pm.newQuery(SQL, "INSERT INTO" + ps.darTablaPromocion() + "(descripcion, fechaFin, cantidadProductos, id) values(?,?,?,?)"  );
		q.setParameters(descripcion, fechaFin, cantidadProductos, id);
		return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecutra una sentencia SQL que elimina una promoción de la tienda
	 * de acuerdo a su identificador.
	 * @param pm
	 * @param id
	 * @return
	 */
	public long eliminarPromocionPorId(PersistenceManager pm, long id){
		Query q = pm.newQuery(SQL, "DELETE FROM" + ps.darTablaPromocion() + "WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}


	/**
	 * 
	 * @param pm
	 * @param descripcion
	 * @return
	 */
	public long eliminarPromocionPorDescripcion(PersistenceManager pm, String descripcion) {
		Query q = pm.newQuery(SQL, "DELETE FROM" + ps.darTablaPromocion() + "WHERE DESCRIPCION = ?");
		q.setParameters(descripcion);
		return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta una sentencia SQL que permite obtener una promoción 
	 * de la tienda por su identificador
	 * @param pm
	 * @param id
	 * @return
	 */
	public Promocion darPromocionPorId(PersistenceManager pm, long id){
		Query q = pm.newQuery(SQL, "SELECT FROM" + ps.darTablaPromocion() + "WHERE id = ?");
		q.setResultClass(Promocion.class);
		q.setParameters(id);
		return (Promocion) q.executeUnique();
	}





}
