package main.java.uniandes.isis2304.superandes.persistencia;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import main.java.uniandes.isis2304.superandes.negocio.*;

/**
 * Clase para el manejador de persistencia del proyecto Superandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * 	Se apoya en las clase:
 * SQLAlmacenamiento, SQLCategoria, SQLCliente, SQLFactura, SQLPedido, SQLProducto, SQLProductoCategoria,
 * SQLProductoSucursal, SQLProductoProveedor, SQLProductosPedidos, SQLProductosFactura, SQLPromocion,
 * SQLPromociones, SQLProveedor, SQLProveen, SQLSucursal, SQLUtil, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Anderson Barragán
 */
public class PersistenciaSuperandes{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/** Logger para escribir la traza de la ejecución */
	private static Logger log = Logger.getLogger(PersistenciaSuperandes.class.getName());

	/** Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/** Atributo privado que es el único objeto de la clase - Patrón SINGLETON */
	private static PersistenciaSuperandes instance;

	/** Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaSuperandes
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla ALMACENAMIENTO de la base de datos
	 */
	private SQLAlmacenamiento sqlAlmacenamiento;
	private SQLAlmacenamientoProductos sqlAlmacenamientoProductos;
	private SQLCategoria sqlCategoria;
	private SQLCarrito sqlCarrito;
	private SQLCliente sqlCliente;
	private SQLFactura sqlFactura;
	private SQLPedido sqlPedido;
	private SQLProducto sqlProducto;
	private SQLProductoCategoria sqlProductoCategoria;
	private SQLProductoSucursal sqlProductoSucursal;
	private SQLProductoProveedor sqlProductoProveedor;
	private SQLProductosFactura sqlProductosFactura;
	private SQLProductosPedidos sqlProductosPedidos;
	private SQLPromocion sqlPromocion;
	private SQLPromociones sqlPromociones;
	private SQLProveedor sqlProveedor;
	private SQLProveen sqlProveen;
	private SQLSucursal sqlSucursal;

	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaSuperandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("SuperAndes");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("superandes_sequence");
		tablas.add ("S_ALMACENAMIENTO");
		tablas.add ("S_ALMACENAMIENTO_PRODUCTOS");
		tablas.add ("S_CATEGORIA");
		tablas.add ("S_CLIENTE");
		tablas.add ("S_FACTURA");
		tablas.add ("S_PEDIDO");
		tablas.add ("S_PRODUCTO");
		tablas.add ("S_PRODUCTO_CATEGORIA");
		tablas.add ("S_PRODUCTO_SUCURSAL");
		tablas.add ("S_PRODUCTOPROVEEDOR");
		tablas.add ("S_PRODUCTOS_PEDIDOS");
		tablas.add ("S_PRODUCTOSFACTURA");
		tablas.add ("S_PROMOCION");
		tablas.add ("S_PROMOCIONES");
		tablas.add ("S_PROVEEDOR");
		tablas.add ("S_PROVEEN");
		tablas.add ("S_SUCURSAL");
		tablas.add ("S_CARRITO");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaSuperandes existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperandes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaSuperandes existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	private void crearClasesSQL (){
		sqlAlmacenamiento = new SQLAlmacenamiento(this);
		sqlAlmacenamientoProductos = new SQLAlmacenamientoProductos(this);
		sqlCategoria = new SQLCategoria(this);
		sqlCliente = new SQLCliente(this);
		sqlCarrito = new SQLCarrito(this);
		sqlFactura = new SQLFactura(this);
		sqlPedido = new SQLPedido(this);
		sqlProducto = new SQLProducto (this);
		sqlProductoCategoria = new SQLProductoCategoria(this);
		sqlProductoSucursal = new SQLProductoSucursal(this);
		sqlProductoProveedor = new SQLProductoProveedor(this);
		sqlProductosPedidos = new SQLProductosPedidos(this);
		sqlProductosFactura = new SQLProductosFactura(this);
		sqlPromocion = new SQLPromocion(this);
		sqlPromociones = new SQLPromociones(this);
		sqlProveedor = new SQLProveedor(this);
		sqlProveen = new SQLProveen(this);
		sqlSucursal= new SQLSucursal(this);
		sqlUtil = new SQLUtil(this);
	}

	public String darSeqSuperandes ()
	{
		return tablas.get (0);
	}
	public String darTablaAlmacenamiento ()
	{
		return tablas.get (1);
	}
	public String darTablaAlmacenamientoProductos ()
	{
		return tablas.get (2);
	}
	public String darTablaCategoria ()
	{
		return tablas.get (3);
	}
	public String darTablaCarrito ()
	{
		return "S_CARRITO";
	}
	public String darTablaCliente ()
	{
		return tablas.get (4);
	}
	public String darTablaFactura ()
	{
		return tablas.get (5);
	}
	public String darTablaPedido ()
	{
		return tablas.get (6);
	}
	public String darTablaProducto ()
	{
		return tablas.get (7);
	}
	public String darTablaProductoCategoria ()
	{
		return tablas.get (8);
	}
	public String darTablaProductoSucursal ()
	{
		return tablas.get (9);
	}
	public String darTablaProductoProveedor ()
	{
		return tablas.get (10);
	}
	public String darTablaProductosPedidos ()
	{
		return tablas.get (11);
	}
	public String darTablaProductosFactura ()
	{
		return tablas.get (12);
	}
	public String darTablaPromocion()
	{
		return tablas.get (13);
	}
	public String darTablaPromociones ()
	{
		return tablas.get (14);
	}
	public String darTablaProveedor ()
	{
		return tablas.get (15);
	}
	public String darTablaProveen ()
	{
		return tablas.get (16);
	}
	public String darTablaSucursal ()
	{
		return tablas.get (17);
	}

	/**
	 * Transacción para el generador de secuencia de Superandes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Superandes
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	//_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_->
	//_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_->
	/*                           METODOS DE USADOS PARA LA OBTENCIÓN DE RESULTADOS NO ADMINISTRATIVOS                        */
	//_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_->
	//_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_->
	
	/** *********************************************************************************************************************/
	//_______________________________________________________________________________________________________________________
	/* **********************************************************************************************************************
	 * 									ITERACIÓN 1 REQUERIMIENTOS FUNCIONALES
	 ************************************************************************************************************************/

	public Promocion adicionarPromocion(String descripcion, String fechafin, int cantidadProductos)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idPromo = nextval ();
			long tuplasInsertadas = sqlPromocion.adicionarPromocion(pm, descripcion, fechafin, cantidadProductos, idPromo);
			tx.commit();

			log.trace ("Inserción de una promoción: " + descripcion + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Promocion(idPromo, descripcion, fechafin, cantidadProductos);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Pedido registrarPedido(double precioPedido, String fechaAcordada, long proveedor, long sucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlPedido.adicionarPedido(pm, id, "01/01/2001", 0, 0, precioPedido, fechaAcordada, proveedor, sucursal);
			tx.commit();

			log.trace ("Inserción del pedido: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Pedido();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long finalizarPromoPorId (long idPromo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPromocion.eliminarPromocionPorId(pm, idPromo);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public long finalizarPromoPorDescripcion(String descripcion) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPromocion.eliminarPromocionPorDescripcion(pm, descripcion);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Almacenamiento
	 * @return La lista de objetos Almacenamiento, construidos con base en las tuplas de la tabla S_ALMACANAMIENTOS
	 */
	public List<Almacenamiento> darAlmacenamientos ()
	{
		return sqlAlmacenamiento.darAlmacenamientos(pmf.getPersistenceManager());
	}

	/**
	 * Método que consulta todas las tuplas en la tabla TipoBebida
	 * @return La lista de objetos TipoBebida, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<Carrito> darCarritosDisponibles (long sucursal)
	{
		return sqlCarrito.darCarritosDisponibles(pmf.getPersistenceManager(), sucursal);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla TipoBebida con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public Almacenamiento darAlmacenamientoPorId (long idAlmacenamiento)
	{
		return sqlAlmacenamiento.darAlmacenamientoPorId (pmf.getPersistenceManager(), idAlmacenamiento);
	}

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperandes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarSuperandes (pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	/* ****************************************************************
	 * 			REQUERIMIENTO 1
	 *****************************************************************/
	/**
	 * @param nombre
	 * @return
	 */
	public Proveedor adicionarProveedor(String nombre)	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			long nitProveedor = nextval ();
			long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, nitProveedor, nombre);
			tx.commit();

			log.trace ("Inserción de Proveedor: " + nitProveedor + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Proveedor(nitProveedor, nombre);
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			REQUERIMIENTO 2
	 *****************************************************************/
	/**
	 * 
	 * @param nombre
	 * @param marca
	 * @param presentacion
	 * @param empacado
	 * @param cantPresentacion
	 * @param volumenEmpaque
	 * @param peso
	 * @param fechavencimiento
	 * @param unidadMedida
	 * @param categoria
	 * @return
	 */
	public Producto adicionarProducto(String nombre, String marca, String presentacion, String empacado, int cantPresentacion, double volumenEmpaque, double peso, String fechavencimiento, String unidadMedida, long categoria)	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			long idProducto = nextval ();
			long tuplasInsertadas = sqlProducto.adicionarProducto(pm, idProducto, nombre, marca, presentacion, empacado, fechavencimiento, volumenEmpaque, peso, cantPresentacion, unidadMedida);
			tx.commit();

			log.trace ("Inserción de producto: " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");

			sqlProductoCategoria.adicionarProductoCategoria(pm, categoria, idProducto);
			log.trace ("Inserción de una tupla a producto categoria: " + idProducto + "-"+ categoria + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Producto(nombre, marca, presentacion, unidadMedida, empacado, cantPresentacion, volumenEmpaque, peso, fechavencimiento, idProducto);
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public Factura registrarFactura(String FECHAFACTURA, long DESUCURSAL, long DECLIENTE) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			long numFactura = nextval ();
			long tuplasInsertadas = sqlFactura.adicionarFactura(pm, numFactura, FECHAFACTURA, DESUCURSAL, DECLIENTE);
			tx.commit();

			log.trace ("Inserción de una Factura: " + numFactura + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Factura(numFactura, FECHAFACTURA,DESUCURSAL, DECLIENTE);
		} catch (Exception e)	{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}
	/* ****************************************************************
	 * 			REQUERIMIENTO 3
	 *****************************************************************/
	/**
	 * 
	 * @param nombre
	 * @param puntos
	 * @param email
	 * @param tipo
	 * @param direccion
	 * @return
	 */
	public Cliente adicionarCliente(String nombre, int puntos, String email, String tipo, String direccion)	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			long identificacion = nextval ();
			long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, identificacion, nombre);
			tx.commit();

			log.trace ("Inserción de Proveedor: " + identificacion + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cliente(identificacion, nombre, puntos, email, tipo, direccion);
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			REQUERIMIENTO 4
	 *****************************************************************/
	/**
	 * 
	 * @param nombre
	 * @param direccion
	 * @param ciudad
	 * @param segmentoMercado
	 * @param tamanio
	 * @return
	 */
	public Sucursal adicionarSucursal(String nombre, String direccion, String ciudad, String segmentoMercado, double tamanio)	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			long identificacion = nextval ();
			long tuplasInsertadas = sqlSucursal.adicionarSucursal(pm, identificacion, nombre, direccion, ciudad, segmentoMercado, tamanio);
			tx.commit();

			log.trace ("Inserción de Sucursal: " + identificacion + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Sucursal(identificacion, nombre, direccion, segmentoMercado, tamanio, ciudad);
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			REQUERIMIENTO 5 && 6
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Almacenamiento
	 * Adiciona entradas al log de la aplicación
	 * @param peso el peso
	 * @param volumen el volumen
	 * @param pesoMax
	 * @param volumenMax
	 * @param tipo
	 * @param categoria
	 * @param nivelAbastecimiento
	 * @param sucursal
	 * @return
	 */
	public Almacenamiento adicionarAlmacenamiento(double peso, double volumen, double pesoMax, double volumenMax, String tipo, long categoria, int nivelAbastecimiento, long sucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idAlmacenamiento = nextval ();
			long tuplasInsertadas = sqlAlmacenamiento.adicionarAlmacenamiento(pm, idAlmacenamiento, peso, pesoMax, volumen, volumenMax, tipo, categoria, nivelAbastecimiento, sucursal);
			tx.commit();

			log.trace ("Inserción de almacenamiento: " + idAlmacenamiento + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Almacenamiento (idAlmacenamiento, peso, pesoMax, volumen, volumenMax, tipo, sucursal, categoria, nivelAbastecimiento);
		}
		catch (Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Carrito tomarCarrito(long idCarrito, long idCliente) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		//tx.setIsolationLevel("repeatable-read");
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCarrito.tomarCarrito(pm, idCarrito, idCliente);
			tx.commit();

			log.trace ("Ocupación del carrito: " + idCarrito + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Carrito (idCarrito, "", idCliente);
		}
		catch (Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Object recolectarProductosDeCarrito(long idCarrito) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			//long tuplasInsertadas = sqlAlmacenamiento.recolectarProductosDeCarrito(pm, idCarrito);
			tx.commit();

			//log.trace ("Ocupación del carrito: " + idCarrito + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Object();
		}
		catch (Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public AlmacenamientoProductos adicionarProductoACarrito(long idCarrito, long idProducto, int cantidad) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			long tuplasInsertadas = sqlCarrito.adicionarProductoACarrito(pm, idCarrito, idProducto, cantidad);
			tx.commit();

			log.trace ("Añadiendo producto: " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");

			return new AlmacenamientoProductos(idProducto, idCarrito, cantidad);
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public Carrito adicionarCarrito(long idCarrito) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCarrito.aniadirCarrito(pm, idCarrito);
			tx.commit();

			log.trace ("Inserción de carrito: " + idCarrito + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Carrito (idCarrito, Carrito.DISPONIBLE, 0);
		}
		catch (Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Producto darProductoPorId(long idProducto) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			Producto tuplasInsertadas = sqlProducto.darProductoPorId(pm, idProducto);
			tx.commit();

			log.trace ("Añadiendo producto: " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");

			return tuplasInsertadas;
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public AlmacenamientoProductos actualizarProductoAlmacenamiento(long idAlmacenamiento, long idProducto,
			int cantidad, boolean b) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			AlmacenamientoProductos tuplasInsertadas = sqlAlmacenamientoProductos.actualizarProductoAlmacenamiento(pm, idAlmacenamiento, idProducto, cantidad, b);
			tx.commit();

			log.trace ("Añadiendo producto: " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");

			return tuplasInsertadas;
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public Carrito cambiarEstadoCarrito(long id_carrito, String nuevoEstado) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCarrito.cambiarEstadoCarrito(pm, id_carrito, nuevoEstado);
			tx.commit();

			log.trace ("Inserción de carrito: " + id_carrito + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Carrito (id_carrito, Carrito.DISPONIBLE, 0);
		}
		catch (Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	private Object getOfField(PersistenceManager pm, String nombreClase, String condiciones, String nombreMetodo) {
		log.info ("		en getOfField >>>>> Se llama al metodo:		-" + nombreMetodo);
		switch (nombreClase) {
		case "Almacenamiento":
			return sqlAlmacenamiento.getElement(pm, condiciones);
		case "AlmacenamientoProductos":
			//			Object ap = null;
			//			try	{
			//				Method apm = AlmacenamientoProductos.class.getMethod ( nombreMetodo , PersistenceManager.class, String.class);
			//				AlmacenamientoProductos apInstance = new AlmacenamientoProductos();
			//				ap = apm.invoke(apInstance, pm, condiciones);
			//			} 	catch (Exception e) {log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));} 
			return sqlAlmacenamientoProductos.getElement(pm, condiciones);
		case "Categoria":
			return sqlCategoria.getElement(pm, condiciones);
		case "Cliente":
			return sqlCliente.getElement(pm, condiciones);
		case "Carrito":
			return sqlCarrito.getElement(pm, condiciones);
			//		case "Factura":
			//			return sqlFactura.getElement(pm, condiciones);
			//		case "Pedido":
			//			return sqlPedido.getElement(pm, condiciones);
			//		case "Producto":
			//			return sqlProducto.getElement(pm, condiciones);
			//		case "ProductoCategoria":
			//			return sqlProductoCategoria.getElement(pm, condiciones);
			//		case "ProductoSucursal":
			//			return sqlProductoSucursal.getElement(pm, condiciones);
			//		case "ProductoProveedor":
			//			return sqlProductoProveedor.getElement(pm, condiciones);
			//		case "ProductosPedidos":
			//			return sqlProductosPedidos.getElement(pm, condiciones);
			//		case "ProductosFactura":
			//			return sqlProductosFactura.getElement(pm, condiciones);
			//		case "Promocion":
			//			return sqlPromocion.getElement(pm, condiciones);
			//		case "Promociones":
			//			return sqlPromociones.getElement(pm, condiciones);
			//		case "Proveedor":
			//			return sqlProveedor.getElement(pm, condiciones);
			//		case "Proveen":
			//			return sqlProveen.getElement(pm, condiciones);
		case "Sucursal":
			return sqlSucursal.getElement(pm, condiciones);
		default:
			return null;	
		}
	}

	private Object getOfFieldWithSentence(PersistenceManager pm, String nombreClase, String sentenciaSql) {
		switch (nombreClase) {
		case "Carrito":
			return sqlCarrito.getElementCompleteSentence(pm, sentenciaSql);
		case "AlmacenamientoProductos":
			return sqlAlmacenamientoProductos.getElementCompleteSentence(pm, sentenciaSql);
		case "Almacenamiento":
			return sqlAlmacenamiento.getElementCompleteSentence(pm, sentenciaSql);
		default:
			return null;	
		}
	}

	private List getsOfField(PersistenceManager pm, String nombreClase, String condiciones) {
		switch (nombreClase) {
		case "Carrito":
			return sqlCarrito.getElemets(pm, condiciones);
		case "AlmacenamientoProductos":
			return sqlAlmacenamientoProductos.getElements(pm, condiciones);
		default:
			return null;	
		}
	}
	private long deleteOfField(PersistenceManager pm, String nombreClase, String condiciones) {
		switch (nombreClase) {
		case "Carrito":
			return sqlCarrito.deleteElemets(pm, condiciones);
		case "AlmacenamientoProductos":
			return sqlAlmacenamientoProductos.deleteElements(pm, condiciones);
		default:
			return -1;	
		}
	}

	private List getsOfFieldWithSentence(PersistenceManager pm, String nombreClase, String condiciones) {
		switch (nombreClase) {
		case "Carrito":
			return sqlCarrito.getElemetsCompleteSentence(pm, condiciones);
		case "AlmacenamientoProductos":
			return sqlAlmacenamientoProductos.getElemetsCompleteSentence(pm, condiciones);
		case "Almacenamiento":
			return sqlAlmacenamiento.getElemetsCompleteSentence(pm, condiciones);
			//		case "Categoria":
			//			return sqlCategoria.getElemetsCompleteSentence(pm, condiciones);
			//		case "Cliente":
			//			return sqlCliente.getElemetsCompleteSentence(pm, condiciones);
		default:
			return null;	
		}
	}

	public Object getElement(Class clase, String condiciones) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			log.info("Obteniendo la clase: "+clase.getSimpleName()+" ->");
			Object tuplasInsertadas = getOfField(pm, clase.getSimpleName(), condiciones, "getElement");
			log.trace("Objeto obtenido: "+ tuplasInsertadas);
			return tuplasInsertadas;
		}		catch (Exception e)		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())	tx.rollback();
			pm.close();
		}
	}

	public long deleteElements(Class<?> clase, String condiciones) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			log.info("Obteniendo la clase: "+clase.getSimpleName()+" ->");
			long tuplasInsertadas = deleteOfField(pm, clase.getSimpleName(), condiciones);
			log.trace("tuplas eliminadas: "+ tuplasInsertadas);
			return tuplasInsertadas;
		}		catch (Exception e)		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally		{
			if (tx.isActive())	tx.rollback();
			pm.close();
		}
	}
	
	public List<Object> getElements(Class<?> clase, String condiciones) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			log.info("Obteniendo la clase: "+clase.getSimpleName()+" ->");
			List<Object> tuplasInsertadas = getsOfField(pm, clase.getSimpleName(), condiciones);
			log.trace("Objeto obtenido: "+ tuplasInsertadas);
			return tuplasInsertadas;
		}		catch (Exception e)		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())	tx.rollback();
			pm.close();
		}
	}

	public Object getElementCompleteSentence(Class<?> clase, String sentenciaSql) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			log.info("Obteniendo la clase: "+clase.getSimpleName()+" ->");
			Object tuplasInsertadas = getOfFieldWithSentence(pm, clase.getSimpleName(), sentenciaSql);
			log.trace("Objeto obtenido: "+ tuplasInsertadas);
			return tuplasInsertadas;
		}		catch (Exception e)		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())	tx.rollback();
			pm.close();
		}
	}

	public List<Object> getElementsCompleteSentence(Class<?> clase, String sentenciaCompleta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			log.info("Obteniendo la clase: "+clase.getSimpleName()+" ->");
			List<Object> tuplasInsertadas = getsOfFieldWithSentence(pm, clase.getSimpleName(), sentenciaCompleta);
			log.trace("Objeto obtenido: "+ tuplasInsertadas);
			return tuplasInsertadas;
		}		catch (Exception e)		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())	tx.rollback();
			pm.close();
		}
	}

	public void deleteElement() {
		//ejemplo
		//	public long eliminarCategoriasNoServidas (PersistenceManager pm)
		//	{
		//		String q2Str = "SELECT idCategoria FROM " + pp.darTablaSirven ();
		//		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCategoria () + " WHERE id NOT IN (" + q2Str + ")");
		//		return (long) q.executeUnique();            
		//	}
	}

	public void Prueba () {
		}

	/* **********************************************************************************************************************
	 * 									TESTS
	 ************************************************************************************************************************/

	public List<Object[]> listarProductosDeCarrito(long idCarrito) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			List<Object[]> tuplasInsertadas = sqlCarrito.listarProductosDeCarrito(pm, idCarrito);
			tx.commit();

			log.trace ("Añadiendo producto: " + idCarrito + ": " + tuplasInsertadas + " tuplas insertadas");

			return tuplasInsertadas;
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public Timestamp fechaInicioSuperandes(){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			Timestamp rta = sqlFactura.fechaInicioSuperandes(pm);
			tx.commit();

			return rta;
		}	catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public List<Long> darSucursales(){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			List<Long> rta = sqlSucursal.darSucursales(pm);
			tx.commit();

			return rta;
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public Long[] darSucursalesDemandaPrecio(long idSucursal, long codigoBarras, Timestamp fechaInicio, Timestamp fechaFin){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			Long[] rta = sqlSucursal.darSucursalesDemandaPrecio(pm, idSucursal, codigoBarras, fechaInicio, fechaFin);
			tx.commit();

			return rta;
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public List<Long> darClientesSucursal(long idSucursal){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();
			List<Long> rta = sqlCliente.darClientesSucursal(pm, idSucursal);
			tx.commit();

			return rta;
		}		catch (Exception e)		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally	{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}
	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									CICLO 2 REQUERIMIENTOS DE CONSULTA
	 ************************************************************************************************************************/

	public int facturarCarrito(long idCarrito, long idSucursal, long idCliente) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		int numProductosFacturados= 0;
		try	{
			tx.begin();

			//Obtengo la lista de productos del carrito
			List<long []> productosCarrito = new LinkedList<long []> ();
			List<Object[]> resp = sqlCarrito.listarProductosDeCarrito(pm, idCarrito);

			//Identificador de la factura.
			Long idFactura= nextval();

			//Fecha actual
			GregorianCalendar x= new GregorianCalendar();
			String fechaActual= (x.get(Calendar.YEAR)) + "/";
			fechaActual+= x.get(Calendar.MONTH) + "/";
			fechaActual+=x.get(Calendar.DAY_OF_MONTH);
			Timestamp fechaAcordada= new Timestamp((x.get(Calendar.YEAR)-1900), x.get(Calendar.MONTH), x.get(Calendar.DATE), 0, 0, 0, 0);


			//Ingreso todos los productos de la respuesta en el array de productosCarrito
			for ( Object [] tupla : resp)
			{
				long [] datosResp = new long [2];

				//id del producto
				datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
				//cantidad de productos
				datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
				productosCarrito.add (datosResp);
			}

			//Generar factura 
			sqlFactura.adicionarFactura(pm, idFactura, fechaAcordada, idSucursal, idCliente);

			//Facturar cada uno de los productos.
			for ( long[] producto : productosCarrito)
			{
				long codigoBarras = producto[0];
				long cantidadFacturada = producto[1];
				sqlProductosFactura.adicionarProductosFactura(pm, idFactura, codigoBarras, cantidadFacturada);
				numProductosFacturados++;
				pagarProducto(idSucursal, codigoBarras, fechaAcordada);
			}
			//Actualizar estado del carrito
			sqlCarrito.cambiarEstadoCarrito(pm, idCarrito, Carrito.DISPONIBLE);			

			tx.commit();
			log.trace ("Añadiendo producto: " + idCarrito + ": " + " tuplas insertadas");

			return numProductosFacturados;
		}		
		catch (Exception e)	{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return 0;
		}
		finally	{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public boolean pagarProducto(long idSucursal, long codigoBarras, Timestamp fechaAcordada) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try	{
			tx.begin();

			//Nivel de reorden del producto.
			int nivelReorden= (int) sqlProductoSucursal.nivelDeReorden(pm, idSucursal, codigoBarras);

			//Revisar el estado del estante del producto
			long [] estante = sqlAlmacenamiento.nivelAlmacenamientoEstante(pm, codigoBarras).get(0);
			long idEstante = estante[0];
			long cantidadProductosEstante= estante[1];

			//Revisar el estado de la bodega del producto.
			long [] bodega = sqlAlmacenamiento.nivelAlmacenamientoBodega(pm, codigoBarras).get(0);
			long idBodega = bodega[0];
			long cantidadProductosBodega= bodega[1];

			//comparar si es necesaria la descarga de inventario
			if(cantidadProductosEstante<nivelReorden){
				sqlAlmacenamientoProductos.restarProductosEstanteBodega(pm, idBodega, nivelReorden*2);
				sqlAlmacenamientoProductos.sumarProductosEstanteBodega(pm, idEstante, nivelReorden*2);
			}
			//Revisar el estado de la bodega.
			if(cantidadProductosBodega<nivelReorden){
				List<long[]> proveedores = sqlProveen.darProveedorProducto(pm, codigoBarras, idSucursal);
				long idProveedor = 0;
				long menorPrecio=Long.MAX_VALUE;
				for (long[] ls : proveedores) {
					if (ls[1]<menorPrecio) {
						menorPrecio=ls[1];
						idProveedor= ls[0];
					}
				}
				if(idProveedor!=0){
					Long idPedido= nextval();
					sqlPedido.adicionarPedido(pm, idPedido, null, 0, 0, menorPrecio*nivelReorden*2, fechaAcordada, idProveedor, idSucursal);
				}
				else{
					throw new Exception("No existe un proveedor para el producto con id: " + codigoBarras +" En la sucursal: " + idSucursal);
				}

				tx.commit();
				log.trace ("Se ha comprado el producto:  " + codigoBarras +" se han revisado los respectivos cargues y descargues de inventario");
			}
			return true;
		}	
		catch (Exception e)	{
			// e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return false;
		}
		finally	{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	/* **********************************************************************************************************************
	 * 									RFC 7 ITERACION 2
	 ************************************************************************************************************************/
	public List<String[]> analizarOperacionSuperAndes(long codigoBarras , int rangoFecha){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();

			GregorianCalendar x= new GregorianCalendar();
			Timestamp fechaFin= new Timestamp((x.get(Calendar.YEAR)-1900), x.get(Calendar.MONTH), x.get(Calendar.DATE), 0, 0, 0, 0);

			//Conversion Fechas, cantidad de tiempo en milisegundos que se debe sumar.
			Long time= (long) rangoFecha*24*60*60*1000;

			//FEcha inicio de la tienda en milisegundos.
			Long fechaInicio= sqlFactura.fechaInicioSuperandes(pm).getTime();


			//lista con los id's de todas las cucursales de la tienda
			List<Long> sucursales = sqlSucursal.darSucursales(pm);

			// Long[] 1)IdSucursal  2)tipo  3)fecha
			List<String[]> sucursalesMenorDemanda = new LinkedList<String[]>();

			for (Long long1 : sucursales) {


				fechaInicio= sqlFactura.fechaInicioSuperandes(pm).getTime();

				Long mayor = Long.MIN_VALUE;
				Long menor = Long.MAX_VALUE;
				Long recaudo = Long.MIN_VALUE;
				String fechaMenor = null;
				String fechaMayor = null;
				String fechaRecaudo = null;

				Timestamp fechaInicioTime= new Timestamp(fechaInicio);
				Timestamp fechaFinTempTime = new Timestamp(fechaInicio+time);

				while(fechaFinTempTime.before(fechaFin)){
					Long[] rta = sqlSucursal.darSucursalesDemandaPrecio(pm, long1, codigoBarras, fechaInicioTime, fechaFinTempTime);
					if(rta.length!=0){
						Long cantidadTemp = rta[1];
						Long recaudoTemp = rta[2]*cantidadTemp;

						//vienen las comparaciones
						if(cantidadTemp<menor){
							menor= cantidadTemp;
							fechaMenor = fechaInicioTime.toString()+"---" + fechaFin.toString();
						}
						if(cantidadTemp>mayor){
							mayor=cantidadTemp;
							fechaMayor = fechaInicioTime.toString()+"---" + fechaFin.toString();
						}
						if(recaudoTemp>recaudo){
							recaudo=recaudoTemp;
							fechaRecaudo= fechaInicioTime.toString()+"---" + fechaFin.toString();
						}
					}
					fechaInicio+=time;
					fechaInicioTime= new Timestamp(fechaInicio);
					fechaFinTempTime = new Timestamp(fechaInicio+time);
				}
				if(fechaMayor!=null && fechaMenor!=null && fechaRecaudo!=null){
					String[] rta= new String[4];
					rta[0] = long1.toString() ;
					rta[1] = "ALTA DEMANDA";
					rta[2] = mayor.toString();
					rta[3] = fechaMayor;

					String[] rta1= new String[4];
					rta1[0] = long1.toString() ;
					rta1[1] = "BAJA DEMANDA";
					rta1[2] = menor.toString();
					rta1[3] = fechaMenor;

					String[] rta2= new String[4];
					rta2[0] = long1.toString() ;
					rta2[1] = "MAYOR RECAUDO";
					rta2[2] = recaudo.toString();
					rta2[3] = fechaRecaudo;

					sucursalesMenorDemanda.add(rta);
					sucursalesMenorDemanda.add(rta1);
					sucursalesMenorDemanda.add(rta2);
				}
			}

			tx.commit();
			log.trace ("Se han añadido en sucursalesMenorDemanda " + sucursalesMenorDemanda.size());


			//			for (String[] strings : sucursalesMenorDemanda) {
			//				System.out.println(strings[0] + "--" +strings[1] + "--" +strings[2]+ "--" + strings[3] );
			//			}
			return sucursalesMenorDemanda;

		}	
		catch (Exception e)	{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally	{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}


	/* **********************************************************************************************************************
	 * 									RFC 8 ITERACION 2
	 ************************************************************************************************************************/
	public List<Cliente> clientesFrecuentes(long idSucursal){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try		{
			tx.begin();

			//Periodo de tiempo de compra.
			Long time= (long) 30*24*60*60*1000;

			//Tiempo acutal operación superandes
			GregorianCalendar x= new GregorianCalendar();
			Timestamp fechaFinTimeSuperandes= new Timestamp((x.get(Calendar.YEAR)-1900), x.get(Calendar.MONTH), x.get(Calendar.DATE), 0, 0, 0, 0);
			long fechaFinSuperandes= fechaFinTimeSuperandes.getTime();


			//Fecha inicio de la tienda en milisegundos.
			Long fechaInicioSuperandes= sqlFactura.fechaInicioSuperandes(pm).getTime();
			Timestamp fechaInicioTimeSuperandes = new Timestamp(fechaInicioSuperandes);

			//Obtener todos los clientes que existen en la sucursal
			List<Long> clientesSucursal= sqlCliente.darClientesSucursal(pm, idSucursal);

			// Todos los clientes frecuentes de la tienda.
			List<Long> clientesFrecuentes= new LinkedList<Long>();

			for (Long idCliente : clientesSucursal) {

				Timestamp fechaInicialTemp= new Timestamp(fechaInicioSuperandes);
				Timestamp fechaFinalTemp = new Timestamp(fechaInicioSuperandes+time);
				int ocurrencias = 0;
				int contadorVeces=0;
				boolean termina = false;
				while(fechaFinalTemp.getTime()< fechaFinSuperandes && !termina){

					//obtener compras cliente en ese periodo de tiempo.
					List<Long> comprasCliente = sqlCliente.darClientesFactura(pm, idSucursal, fechaInicialTemp, fechaFinalTemp, idCliente);
					//si las compras del cliente son mayores a 2
					if(comprasCliente.size()>=2){
						ocurrencias++;
					}
					else{
						termina=true;
					}
					fechaInicialTemp= new Timestamp(fechaInicialTemp.getTime()+time);
					fechaFinalTemp = new Timestamp(fechaFinalTemp.getTime()+time);
					contadorVeces++;
				}
				if(ocurrencias>=contadorVeces){
					clientesFrecuentes.add(idCliente);
				}
			}
			List<Cliente> rta = new LinkedList<Cliente>();
			for (Long long1 : clientesFrecuentes) {
				rta.add(sqlCliente.darClientePorId(pm, long1));
			}

//			for (Cliente cliente : rta) {
//				System.out.println(cliente.getNombre() + " --- " + cliente.getIdentificacion());
//			}
//			System.out.println(idSucursal);

			return rta;
		}
		catch (Exception e) {
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

}
