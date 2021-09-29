/**
 * 
 */
package main.java.uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Promociones;

/**
 * @author Sebastian Mujica
 *
 */
public class SQLPromociones{
	
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
	public SQLPromociones(PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	
	/**
	 * Crea y ejecuta una sentencia sql que agrega una realción entre un producto y 
	 * una promoción en la tienda.
	 * @param pm
	 * @param idProducto
	 * @param idPromocion
	 * @return
	 */
	public long adicionarPromociones(PersistenceManager pm, long idProducto, long idPromocion  ){
		Query q = pm.newQuery(SQL, "INSERT INTO" + ps.darTablaPromociones() + "(idProducto, idPromocion) values(?,?)"  );
		q.setParameters(idProducto, idPromocion);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta una sentencia sql que elimina una realción entre un producto y 
	 * una promoción en la tienda.
	 * @param pm
	 * @param idProducto
	 * @param idPromocion
	 * @return
	 */
	public long eliminarPromociones(PersistenceManager pm, long idProducto, long idPromocion ){
		Query q = pm.newQuery(SQL, "DELETE FROM" + ps.darTablaPromociones() + "WHERE idProducto = ? AND idPromocion = ? ");
		q.setParameters(idProducto, idPromocion);
		return (long) q.executeUnique();
	}
	

	/**
	 * Crea y ejecuta una sentencia sql que genera una lista de las relaciones
	 * entre los productos y las promociones de la tienda
	 * @param pm
	 * @return
	 */
	public List<Promociones> darListaPromociones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPromociones ());
		q.setResultClass(Promociones.class);
		List<Promociones> resp = (List<Promociones>) q.execute();
		return resp;
	}

}
