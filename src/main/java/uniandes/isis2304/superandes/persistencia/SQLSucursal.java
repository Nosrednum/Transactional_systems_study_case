/**
 * 
 */
package main.java.uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import main.java.uniandes.isis2304.superandes.negocio.Carrito;
import main.java.uniandes.isis2304.superandes.negocio.Sucursal;

/**
 *  * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto 
 *  SUCURSAL de SUPERANDES
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Sebastian Mujica
 *
 */
public class SQLSucursal {

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
	public SQLSucursal(PersistenciaSuperandes ps) {
		this.ps = ps;
	}

	/**
	 * Crea y ejectura la sentencia SQL para adicionar una Sucursal
	 * a la base de datos de SuperAndes
	 * @param pm
	 * @param idSucursal
	 * @param nombre
	 * @param direccion
	 * @param ciudad
	 * @param segmentoMercado
	 * @param tamanio
	 * @return
	 */
	public long adicionarSucursal(PersistenceManager pm, long idSucursal, String nombre, String direccion, String ciudad, String segmentoMercado, double tamanio ){
		Query q = pm.newQuery(SQL, "INSERT INTO" + ps.darTablaSucursal() + "(idSucursal, nombre, direccion, ciudad, segmentoMercado, tamanio) values(?,?,?,?,?,?)"  );
		q.setParameters(idSucursal, nombre, direccion, ciudad, segmentoMercado, tamanio);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejectura la sentencia SQL para eliminar una Sucursal
	 * de la base de datos de SuperAndes
	 * @param pm
	 * @param idSucursal
	 * @return
	 */
	public long eliminarSucursalPorID(PersistenceManager pm, long idSucursal){
		Query q = pm.newQuery(SQL, "DELETE FROM" + ps.darTablaSucursal() + "WHERE idSucursal = ?");
		q.setParameters(idSucursal);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejectura la sentencia SQL para encontrar la informacion de una Sucursal
	 * de la bae de datos de SuperAndes, por su identificador.
	 * @param pm
	 * @param idSucursal
	 * @return
	 */
	public Sucursal darSucursalPorId(PersistenceManager pm, long idSucursal){
		Query q = pm.newQuery(SQL, "SELECT FROM" + ps.darTablaSucursal() + "WHERE idSucursal = ?");
		q.setResultClass(Sucursal.class);
		q.setParameters(idSucursal);
		return (Sucursal) q.executeUnique();
	}


	public Sucursal getElement(PersistenceManager pm, String condiciones) {
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ ps.darTablaSucursal() +" WHERE "+condiciones);
		q.setResultClass(Sucursal.class);
		return (Sucursal) q.executeUnique();
	}

	public List<Long> darSucursales(PersistenceManager pm){
		String sql = "SELECT idsucursal";
		sql+= " FROM " + ps.darTablaSucursal();
		Query q = pm.newQuery(SQL, sql);
		List<Long> rta = new LinkedList<Long>();
		List<Object> x=  (List<Object>) q.executeList();
		for (Object object : x) {
			Long datosResp= ((BigDecimal) object).longValue ();
			rta.add(datosResp);
		}
		return rta;
	}

	public Long[] darSucursalesDemandaPrecio(PersistenceManager pm, long idSucursal, long codigoBarras, Timestamp fechaInicio, Timestamp fechaFin){
		String sql = "SELECT a.producto, SUM(a.cantidad) , c.precio_sucursal ";
		sql+= " FROM " + ps.darTablaProductosFactura() +" a, ";
		sql+= ps.darTablaFactura() + " b, ";
		sql+= ps.darTablaProductoSucursal() + " c ";
		sql+= "WHERE";
		sql+= " b.desucursal= ?";
		sql+= " AND a.producto= ?";
		sql+= " AND c.sucursal = b.desucursal";
		sql+= " AND c.producto = a.producto";
		sql+= " AND b.fechafactura <= ?";
		sql+= " AND b.fechafactura >= ?";
		sql+= " AND b.numerofactura=a.factura";
		sql+= " GROUP BY";
		sql+= " a.producto , c.precio_sucursal";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal, codigoBarras, fechaFin, fechaInicio);
		List<Long[]> rta = new LinkedList<Long[]>();
		List<Object[]> x=  (List<Object[]>) q.executeList();
		for (Object[] tupla : x) {
			Long[] datosResp = new Long [3];
			datosResp [0] = ((BigDecimal) tupla[0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla[1]).longValue ();
			datosResp [2] = ((BigDecimal) tupla[2]).longValue ();
			rta.add(datosResp);
		}
		if(rta.isEmpty()){
			Long[] vacio = new Long[0];
			return vacio;
		}
		return rta.get(0);
	}

}
