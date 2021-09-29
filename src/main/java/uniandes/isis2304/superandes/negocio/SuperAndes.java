package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import main.java.uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;

/**
 * Clase principal del negocio
 * Satisface todos los requerimientos funcionales del negocio
 *
 * @author Anderson Barragán
 */
public class SuperAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(SuperAndes.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperandes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public SuperAndes ()
	{
		ps = PersistenciaSuperandes.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public SuperAndes (JsonObject tableConfig)
	{
		ps = PersistenciaSuperandes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia ();
	}

	/* ****************************************************************
	 * 			Métodos para los requerimientos
	 *****************************************************************/

	/**
	 *  Método encargado de agregar una promoción a la base de datos
	 * @param DESCRIPCION descripción de la promo
	 * @param FECHAFIN fecha final de la promoción
	 * @param CANTIDADPRODUCTOS cantidad de productos en la promocion
	 * @return
	 */
	public Promocion adicionarPromocion(String DESCRIPCION, String FECHAFIN, int CANTIDADPRODUCTOS)
	{
		log.info ("Adicionanda promocion: " + DESCRIPCION + "paspirajurilijillo");
		Promocion almTemp = ps.adicionarPromocion(DESCRIPCION, FECHAFIN, CANTIDADPRODUCTOS);
		log.info ("Adicionanda promocion: " + almTemp);
		return almTemp;
	}

	public long finalizarPromoPorDescripcion (String Descripcion)
	{
		log.info ("Eliminando promo con descripcion: " + Descripcion);
		long resp = ps.finalizarPromoPorDescripcion(Descripcion);	
		log.info ("Eliminando promo con descripcion: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina un tipo de bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El id del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long finalizarPromoPorId (long idPromo)
	{
		log.info ("Eliminando promo con id: " + idPromo);
		long resp = ps.finalizarPromoPorId(idPromo);	
		log.info ("Eliminando promo con id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * registra un nuevo pedido en la base de datos 
	 * @param precioPedido precio del pedido
	 * @param fechaAcordada fecha en la que se acuerda llegará el pedido
	 * @param proveedor proveedor encargado del pedido
	 * @param sucursal sucursal la cual pide el pedido
	 * @return el pedido creado
	 */
	public Pedido registrarPedido(double precioPedido, String fechaAcordada, long proveedor, long sucursal)
	{
		log.info ("Adicionando pedido: " + proveedor +"-"+ sucursal + "paspirajurilijillo");
		Pedido almTemp = ps.registrarPedido(precioPedido, fechaAcordada, proveedor, sucursal);
		log.info ("Adicionanda promocion: " + almTemp);
		return almTemp;
	}

	/**
	 * registra una venta al la BD
	 * @param FECHAFACTURA fecha de la venta
	 * @param DESCRIPCION descripcion añadida por el usuario
	 * @param TOTAL total de la factura
	 * @param DESUCURSAL sucursal en la cual es emitida la factura
	 * @param DECLIENTE cliente propietario de la factura (id)
	 * @return registro factura
	 */
	public Factura registrarFactura(String FECHAFACTURA, long DESUCURSAL, long DECLIENTE)
	{
		log.info ("Adicionando factura: " + DESUCURSAL + "-"+ DECLIENTE +"paspirajurilijillo");
		Factura almTemp = ps.registrarFactura(FECHAFACTURA, DESUCURSAL, DECLIENTE);
		log.info ("Adicionanda promocion: " + almTemp);
		return almTemp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los ALMACENAMIENTOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public Almacenamiento adicionarAlmacenamiento (double peso, double pesoMax, double volumen, double volumenMax, String tipo, long categoria, int nivelAbastecimiento,
			long sucursal)
	{
		log.info ("Adicionando: " + tipo);
		Almacenamiento almTemp = ps.adicionarAlmacenamiento(peso, volumen, pesoMax, volumenMax, tipo, categoria, nivelAbastecimiento, sucursal);		
		log.info ("Adicionando Almacenamiento: " + almTemp);
		return almTemp;
	}

	public Carrito tomarCarrito(long idCarrito, long idCliente) {
		log.info ("Ocupando el carrito: " + idCarrito);
		Carrito caTemp = ps.tomarCarrito(idCarrito, idCliente);	
		log.info ("Adicionando Almacenamiento: " + caTemp);
		return caTemp;
	}

	//______________________________________________________________________________________

	public void tomarCarritoAbandonado() {
		//		log.info ("Ocupando el carrito: " + idCarrito);
		//		Carrito caTemp = pp.tomarCarrito(idCarrito, idCliente);	
		//		log.info ("Adicionando Almacenamiento: " + caTemp);
		//		return caTemp;
	}

	public Carrito cambiarEstadoCarrito(long id_carrito, String nuevoEstado) {
		log.info ("Ocupando el carrito: " + id_carrito);
		Carrito caTemp = ps.cambiarEstadoCarrito(id_carrito, nuevoEstado);	
		log.info ("Adicionando Almacenamiento: " + caTemp);
		return caTemp;
	}

	public void recolectarProductosDeCarrito(long idCarrito) {
		//		log.info ("Ocupando el carrito: " + idCarrito);
		//		caTemp = pp.recolectarProductosDeCarrito(idCarrito);
		//		log.info ("Adicionando Almacenamiento: " + caTemp);
		//		return caTemp;
	}

	public void recolectarCarritos() {

	}
	//______________________________________________________________________________________


	public List<Carrito> listarCarritosDisponibles(long sucursal){
		log.info ("Consultando carritos...");
		List<Carrito> carritos = ps.darCarritosDisponibles(sucursal);
		log.info ("Consultando carritos: " + carritos.size() + " existentes");
		return carritos;
	}
	

	/**
	 * Encuentra todos los almacenamientos en Superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Almacenamiento con todos los almacenamientos que conoce la aplicación, llenos con su información básica
	 */
	public List<Almacenamiento> darAlmacenamientos()
	{
		log.info ("Consultando almacenamientos");
		List<Almacenamiento> almacenamiento = ps.darAlmacenamientos();
		log.info ("Consultando almacenamientos: " + almacenamiento.size() + " existentes");
		return almacenamiento;
	}

	/**
	 * Encuentra todos los tipos de bebida en Superandes y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOAlmacenamiento> darVOAlmacenamientos()
	{
		log.info ("Generando los VO de Almacenamiento");        
		List<VOAlmacenamiento> voAlms = new LinkedList<VOAlmacenamiento> ();
		for (Almacenamiento al : ps.darAlmacenamientos())
		{
			voAlms.add (al);
		}
		log.info ("Generando los VO de almacenamientos: " + voAlms.size() + " existentes");
		return voAlms;
	}

	public VOCarrito aniadirCarrito(long idCarrito) {
		log.info ("Adicionando carrito: " + idCarrito);
		Carrito caTemp = ps.adicionarCarrito(idCarrito);		
		log.info ("Adicionando Almacenamiento: " + caTemp);
		return caTemp;
	}

	public AlmacenamientoProductos adicionarProductoACarrito(long ca, long idProducto, int cantidad) {
		log.info ("Adicionando: " + ca);
		AlmacenamientoProductos almTemp = ps.adicionarProductoACarrito(ca, idProducto, cantidad);	
		log.info ("Adicionando Almacenamiento: " + almTemp);
		return almTemp;
	}

	public VOProducto darProductoPorId(long idProducto) {
		log.info ("Adicionando a carrito: " + idProducto);
		Producto almTemp = ps.darProductoPorId(idProducto);	
		log.info ("Adicionando en carrito: " + almTemp);
		return almTemp;
	}


	public VOAlmacenamientoProductos actualizarProductoAlmacenamiento(long idAlmacenamiento, long idProducto,
			int cantidad, boolean b) {
		log.info ("Adicionando a carrito: " + idProducto);
		AlmacenamientoProductos almTemp = ps.actualizarProductoAlmacenamiento(idAlmacenamiento, idProducto, cantidad, b);
		log.info ("Adicionando en carrito: " + almTemp);
		return almTemp;
	}

	/* ****************************************************************
	 * 			REQUERIMIENTOS FUNCIONALES DE CONSULTA ITERACION 1
	 *****************************************************************/
	public String RFC1 (String fecha)
	{
		log.info ("Consultando la no sé que podrá ser con fecha: " + fecha);
		String pTemp = "BatoMan :v";//se colocaría algo por el estilo: pp.adicionarAlmacenamiento(peso, volumen, pesoMax, volumenMax, tipo, categoria, nivelAbastecimiento, sucursal);		
		log.info ("Se consultó talcosa: " + pTemp);
		return pTemp;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Superandes
	 * @return Un arreglo con 17 números que Indian el número de tuplas borradas en las tablas ALMACENAMIENTO, ALMACENAMIENTOPRODUCTOS
	 * CATEGORIA, CLIENTE, FACTURA, PEDIDOS, PRODUCTOS, PRODUCTOS DE CATEGORIAS, PRODUCTOS DE PROVEEDORES,
	 * PRODUCTOS DE FACTURAS, PRODUCTOS DE PEDIDOS, PRODUCTOS DE SUCURSAL, PROMOCIONES,
	 * PROVEEDORES, PROVEEN y SUCURSALES, respectivamente
	 */
	public long [] limpiarSuperandes ()
	{
		log.info ("Limpiando la BD de Superandes");
		long [] borrrados = ps.limpiarSuperandes();	
		log.info ("Limpiando la BD de Superandes: Listo!");
		return borrrados;
	}



		public Object deleteElements(Class<?> clase, String condiciones) {
			log.info("Eliminando el elemento de tipo: "+clase.getSimpleName());
			Object toRet = ps.deleteElements(clase, condiciones);
			log.info("eliminados: "+ ((toRet != null)?toRet.getClass():"null"));
			return toRet;
		}

	public Object getElement(Class<?> clase, String condiciones) {
		log.info("Obteniendo elemento de tipo: "+clase.getSimpleName());
		Object toRet = ps.getElement(clase, condiciones);
		log.info("Obtenido: "+ ((toRet != null)?""+toRet.getClass():"null"));
		return toRet;
	}

	public Object getElementCompleteSentence(Class<?> clase, String sentenciaSql) {
		log.info("Obteniendo elemento de tipo: "+clase.getSimpleName());
		Object toRet = ps.getElementCompleteSentence(clase, sentenciaSql);;
		log.info("Obtenido: "+ ((toRet != null)?""+toRet.getClass():"null"));
		return toRet;
	}

	public List<Object> getElements(Class<?> clase, String condiciones) {
		log.info("Obteniendo elementos de tipo: "+clase.getSimpleName());
		List<Object> toRet = ps.getElements(clase, condiciones);;
		log.info("Obtenido: "+ ((toRet != null)?""+toRet.getClass():"null"));
		return toRet;
	}

	public List<Object> getElementsCompleteSentence(Class<?> clase, String sentenciaCompleta) {
		log.info("Obteniendo elementos de tipo: "+clase.getSimpleName());
		List<Object> toRet = ps.getElementsCompleteSentence(clase, sentenciaCompleta);;
		log.info("Obtenido: "+ ((toRet != null)?""+toRet.getClass():"null"));
		return toRet;
	}


	/* **********************************************************************************************************************
	 * 									TEST
	 ************************************************************************************************************************/
	//método complementario de sebastián
	public List<Object[]> listarProductosDeCarrito(long idCarrito) {
		return ps.listarProductosDeCarrito(idCarrito);
	}

	/* requerimiento funcional 15*/
	public int facturarCarrito(long idCarrito, long idSucursal, long idCliente){
		log.info("Facturando a: "+
				"carrito: "+ idCarrito +
				" sucursal: "+ idSucursal +
				" cliente: "+ idCliente);
		int a =  ps.facturarCarrito(idCarrito, idSucursal, idCliente);
		log.info("Facturado a: "+
				"carrito: "+ idCarrito +
				" sucursal: "+ idSucursal +
				" cliente: "+ idCliente);
		return a;
	}

	//método complementario de sebastián
	public Timestamp fechaInicio(){
		return ps.fechaInicioSuperandes();
	}

	//método complementario de sebastián
	public List<Long> darSucursales(){
		return ps.darSucursales();
	}

	//método complementario de sebastián
	public Long[] darSucursalesDemandaPrecio(long idSucursal, long codigoBarras, Timestamp fechaInicio, Timestamp fechaFin){
		return ps.darSucursalesDemandaPrecio(idSucursal, codigoBarras, fechaInicio, fechaFin);
	}

	/*req 7 de consulta???*/
	public List<String[]> analizarOperacionSuperAndes(long codigoBarras , int rangoFecha){
		return ps.analizarOperacionSuperAndes(codigoBarras, rangoFecha);
	}

	//método complementario de sebastián
	public List<Long> darClientesSucursal(long idSucursal){
		return ps.darClientesSucursal(idSucursal);
	}

	/*req 8 de consulta*/
	public List<Cliente> clientesFrecuentes(long idSucursal){
		return ps.clientesFrecuentes(idSucursal);
	}



	/* **********************************************************************************************************************
	 * 								----------------
	 ************************************************************************************************************************/

	/* **********************************************************************************************************************
	 * 									CICLO 2
	 ************************************************************************************************************************/



	/* **********************************************************************************************************************
	 * 									-----------
	 ************************************************************************************************************************/

}
