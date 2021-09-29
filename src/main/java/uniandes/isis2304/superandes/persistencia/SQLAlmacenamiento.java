package main.java.uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.datanucleus.store.rdbms.adapter.VirtuosoTypeInfo;

import main.java.uniandes.isis2304.superandes.negocio.Almacenamiento;
import main.java.uniandes.isis2304.superandes.negocio.AlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.Carrito;
import main.java.uniandes.isis2304.superandes.negocio.Producto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto Almacenamiento de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Anderson Barragán
 */
class SQLAlmacenamiento 
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
	private PersistenciaSuperandes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLAlmacenamiento (PersistenciaSuperandes pp)
	{
		this.ps = pp;
	}

	//Crea y ejecuta la sentencia SQL para adicionar un Almacenamiento a la base de datos de Superandes*/
	/**
	 * 
	 * @param pm El manejador de la persistencia
	 * @param idAlmacenamiento identificador del nuevo almacenamiento
	 * @param peso peso actual del almacenamiento {@code inicia en 0}
	 * @param pesoMax peso máximo que soporta el almacenamiento
	 * @param volumen volúmen actual del almacenamiento
	 * @param volumenMax volúmen máximo que soporta el almacenamiento
	 * @param tipo tipo de Almacenamiento siendo: <u>BODEGA</u>, <u>ESTANTE</u> o <u>CARRITO</u>
	 * @param categoria la categoría del almacenamiento <i>CARRITO</i> en caso de ser de tipo {@code CARRITO}
	 * @param nivelAbastecimiento cantidad en la cual se re-ordenan productos para el almacenamiento
	 * @param sucursal sucursal a la cual pertenece el almacenamiento 
	 * @return
	 */
	public long adicionarAlmacenamiento (PersistenceManager pm, long idAlmacenamiento, double peso, double pesoMax, double volumen, double volumenMax,
			String tipo, long categoria, int nivelAbastecimiento, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaAlmacenamiento () + "(ID, PESO, PESO_MAX, VOLUMEN, VOLUMEN_MAX, TIPO, CATEGORIA,"
				+ " NIVEL_ABASTECIMIENTO, SUCURSAL) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(idAlmacenamiento,peso,pesoMax, volumen, volumenMax, tipo, categoria, nivelAbastecimiento, sucursal);
		return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN Almacenamiento de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del Almacenamiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarAlmacenamientoPorId (PersistenceManager pm, long idAlmacenamiento)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaAlmacenamiento () + " WHERE id = ?");
		q.setParameters(idAlmacenamiento);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN Almacenamiento de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del Almacenamiento
	 * @return El objeto ALMACENAMIENTO que tiene el identificador dado
	 */
	public Almacenamiento darAlmacenamientoPorId (PersistenceManager pm, long idAlmacenamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaAlmacenamiento () + " WHERE id = ?");
		q.setResultClass(Almacenamiento.class);
		q.setParameters(idAlmacenamiento);
		return (Almacenamiento) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Almacenamientos de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Almacenamiento
	 */
	public List<Almacenamiento> darAlmacenamientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaAlmacenamiento ());
		q.setResultClass(Almacenamiento.class);
		return (List<Almacenamiento>) q.executeList();
	}
	
	
	/**
	 * Retorna el id del estante y la cantidad de productos que tiene actualmente
	 * @param pm
	 * @param codigoBarras
	 * @return
	 */
	public List<long [] > nivelAlmacenamientoEstante(PersistenceManager pm, Long codigoBarras){
		 String sql = "SELECT a.idalmacenamiento , a.cantidadproductos";
	     sql += " FROM ";
	     sql += ps.darTablaAlmacenamientoProductos() + " a, ";
	     sql += ps.darTablaAlmacenamiento() + " b, ";
	     sql += " WHERE ";
	     sql += "a.idproducto = ?";
	     sql += " AND b.id = a.idalmacenamiento";
	     sql += " AND b.tipo = 'ESTANTE'";
	     Query q = pm.newQuery(SQL, sql);
	     q.setParameters(codigoBarras);
	     List<Object []> query = q.executeList();
 		 List<long []> resp = new LinkedList<long []> ();
	     for ( Object [] tupla : query)
			{
	    			long [] datosResp = new long [2];
	    			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
	    			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
	    			resp.add (datosResp);
			}
	     return resp;
	}
	
	/**
	 * Retorna el id de la bodega y la cantidad de productos que tiene actualmente
	 * @param pm
	 * @param codigoBarras
	 * @return
	 */
	public List<long [] > nivelAlmacenamientoBodega(PersistenceManager pm, Long codigoBarras){
		 String sql = "SELECT a.idalmacenamiento , a.cantidadproductos";
	     sql += " FROM ";
	     sql += ps.darTablaAlmacenamientoProductos() + " a, ";
	     sql += ps.darTablaAlmacenamiento() + " b, ";
	     sql += " WHERE ";
	     sql += "a.idproducto = ?";
	     sql += " AND b.id=a.idalmacenamiento";
	     sql += " AND b.tipo = 'BODEGA'";
	     Query q = pm.newQuery(SQL, sql);
	     q.setParameters(codigoBarras);
	     List<Object []> query = q.executeList();
 		 List<long []> resp = new LinkedList<long []> ();
	     for ( Object [] tupla : query)
			{
	    			long [] datosResp = new long [2];
	    			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
	    			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
	    			resp.add (datosResp);
			}
	     return resp;
	}

	public Almacenamiento getElement(PersistenceManager pm, String condiciones) {
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ ps.darTablaAlmacenamiento() +" WHERE "+condiciones);
		q.setResultClass(Almacenamiento.class);
		return (Almacenamiento) q.executeUnique();
	}

	public List getElemetsCompleteSentence(PersistenceManager pm, String sentenciaCompleta) {
		Query<?> q = pm.newQuery(SQL, sentenciaCompleta);
		q.setResultClass(Almacenamiento.class);
		return (List<Almacenamiento>)q.executeList();
	}

	public Almacenamiento getElementCompleteSentence(PersistenceManager pm, String sentenciaSql) {
		Query q = pm.newQuery(SQL, sentenciaSql);
		q.setResultClass(Almacenamiento.class);
		return (Almacenamiento)q.executeUnique();
	}

}
