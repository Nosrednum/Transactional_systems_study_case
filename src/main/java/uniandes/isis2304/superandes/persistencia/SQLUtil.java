package main.java.uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
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
	public SQLUtil (PersistenciaSuperandes pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqSuperandes () + ".nextval FROM DUAL");
		q.setResultClass(Long.class);
		long resp = (long) q.executeUnique();
		return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperandes (PersistenceManager pm)
	{
		/*
		 * esto es para limpiar las tablas
		 */
		Query qAlmacenamiento 	= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamiento ());          
		Query qAlmacenamientoProductos = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamientoProductos ());
		Query qCategoria 		= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCategoria ());
		Query qCliente 	= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente ());
		Query qFactura 	= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura ());
		Query qPedido 	= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido ());
		Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto ());
		Query qProductoCategoria = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoCategoria ());          
		Query qProductoProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoProveedor ());
		Query qProductosFactura  = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductosFactura ());
		Query qProductosPedidos	 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductosPedidos ());
		Query qProductoSucursal  = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura ());
		Query qPromocion 		 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion ());
		Query qPromociones 		 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromociones());
		Query qProveedor		 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor ());
		Query qProveen 			 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveen ());
		Query qSucursal			 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal ());

		long almacenamientosEliminados = (long) qAlmacenamiento.executeUnique ();//0
		long almacenamientosProductosEliminados = (long) qAlmacenamientoProductos.executeUnique ();//1
		long categoriasEliminadas = (long) qCategoria.executeUnique ();//2
		long clientesEliminados = (long) qCliente.executeUnique ();//3
		long facturasEliminadas = (long) qFactura.executeUnique ();//4
		long pedidosEliminados = (long) qPedido.executeUnique ();//5
		long productosEliminados = (long) qProducto.executeUnique ();//6
		long productosCategoriasEliminados = (long) qProductoCategoria.executeUnique ();//7
		long productosProveedorEliminados = (long) qProductoProveedor.executeUnique ();//8
		long productosFacturasEliminadas = (long) qProductosFactura.executeUnique ();//9
		long productosPedidosEliminados = (long) qProductosPedidos.executeUnique ();//10
		long productosSucursalEliminados = (long) qProductoSucursal.executeUnique ();//11
		long promocionEliminados = (long) qPromocion.executeUnique ();//12
		long promocionesEliminados = (long) qPromociones.executeUnique ();//13
		long proveedoresEliminados = (long) qProveedor.executeUnique ();//14
		long proveensEliminadas = (long) qProveen.executeUnique ();//15
		long sucursalesEliminadas = (long) qSucursal.executeUnique ();//16

		return new long[] {almacenamientosEliminados, almacenamientosProductosEliminados, categoriasEliminadas, clientesEliminados, 
				facturasEliminadas, pedidosEliminados, productosEliminados, productosCategoriasEliminados, productosProveedorEliminados,
				productosFacturasEliminadas, productosPedidosEliminados, productosSucursalEliminados, promocionEliminados, promocionesEliminados,
				proveedoresEliminados, proveensEliminadas, sucursalesEliminadas};
	}
}
