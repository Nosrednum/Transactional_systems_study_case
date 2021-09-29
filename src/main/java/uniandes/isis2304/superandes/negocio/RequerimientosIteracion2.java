package main.java.uniandes.isis2304.superandes.negocio;

import java.util.List;

public class RequerimientosIteracion2 {

	/**
	 * clase principal que permite la coneccion con la persistencia
	 */
	SuperAndes superandes;

	/**
	 * contructor de la clase que inicializa {@code superandes} con el valor dado
	 * @param superandes instancia de la clase con asociación a la persistencia
	 */
	public RequerimientosIteracion2(SuperAndes superandes) {
		this.superandes = superandes;
	}

	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									Req 12
	 ************************************************************************************************************************/
	/**
	 * toma el carrito en caso de que no se sepa el id del mismo
	 * @param documento nuevo cliente asociado al carrito
	 * @param carritos todos los carritos disponibles
	 * @return el carrito que fue asociado 
	 * @throws Exception en caso de que no hubiese carrito a asociar con el cliente
	 */
	public Carrito solicitarCarrito(long documento, List<Carrito> carritos, long sucursal) throws Exception{
		if(carritos == null || carritos.isEmpty())throw new Exception("Lo lamento, parece que no hay carritos que puedas tomar \n por favor pide que añadan uno");
		if(documento <=0)throw new Exception("Error: documento inválido");
		Carrito Dtemp = null;
		Carrito ca = null;
		for (Carrito c : carritos) 
			if(c.estado.equals(Carrito.DISPONIBLE)) {
				Dtemp = c;
				break;
			}
		if(Dtemp != null) ca = superandes.tomarCarrito(Dtemp.id_carrito, documento);
		else 
			ca = tomarCarritoAbandonado(documento, sucursal);

		if(ca == null)throw new Exception("No hay carritos que puedas tomar en este momento :c");
		return ca;		
	}
	/**
	 * toma el carrito especificado en por el id
	 * @param idCarrito carrito a tomar
	 * @param documento cliente que será asociado al carrito
	 * @return el carrito asociado al cliente
	 * @throws Exception en caso de que no se pueda asociar el cliente al carrito
	 */
	public Carrito solicitarCarrito(long idCarrito, long sucursal, long documento) throws Exception{
		String sentencia = "SELECT ID_CARRITO, ESTADO, DECLIENTE FROM (S_ALMACENAMIENTO INNER JOIN S_CARRITO ON ID = ID_CARRITO)"
				+ " WHERE SUCURSAL = "+ sucursal
				+ " AND ID_CARRITO = "+idCarrito;
		Carrito cat = (Carrito)superandes.getElementCompleteSentence(Carrito.class, sentencia);
		if(cat == null || cat.estado.compareTo(Carrito.OCUPADO)== 0)throw new Exception("El carro está ocupado o no existe en esta sucursal, por favor prueba de nuevo");
		Carrito ca = superandes.tomarCarrito(idCarrito, Long.valueOf(documento));
		return ca;
	}
	/**
	 * toma un carrito abandonado, para esto primero desocupa el carrito y luego si lo asocia al cliente
	 * @param cliente cliente que será asociado al carrito
	 * @param sucursal sucursal del carrito 
	 * @return el carrito al cual fue asociado el cliente
	 * @throws Exception en caso de que el carrito no exista
	 */
	private Carrito tomarCarritoAbandonado(long cliente, long sucursal) throws Exception{
		String sentencia = "SELECT ID_CARRITO, ESTADO, DECLIENTE FROM (S_ALMACENAMIENTO INNER JOIN S_CARRITO ON ID = ID_CARRITO)"
				+ " WHERE SUCURSAL = "+ sucursal
				+ " AND ESTADO = '"+Carrito.ABANDONADO+"'";
		List<Object> carritosAbandonados = superandes.getElementsCompleteSentence(Carrito.class, sentencia);
		if(carritosAbandonados == null || carritosAbandonados.isEmpty())throw new Exception("Al parecer no quedan siquiera carritos abandonados");
		Carrito ca = (Carrito)carritosAbandonados.get(0);
		if(ca == null)throw new Exception("Tampoco hay carritos abandonados");
		recolectarCarrito(ca);
		ca = superandes.tomarCarrito(ca.id_carrito, cliente);
		return ca;
	}

	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									REQ 13
	 ************************************************************************************************************************/
	/**
	 * añade un nuevo producto o a la cantidad de uno ya existente al carrito
	 * @param idCarrito carrito al que se le añade el producto
	 * @param idProducto producto que se le añade al carrito
	 * @param cantidad cantidad de productos a añadir
	 * @return el producto añadido al carrito
	 * @throws Exception en caso de que el producto no haya podido ser añadido
	 */
	public AlmacenamientoProductos aniadirProducto(long idCarrito, long idProducto, int cantidad) throws Exception{
		AlmacenamientoProductos producto = (AlmacenamientoProductos)superandes.getElement(AlmacenamientoProductos.class, "IDALMACENAMIENTO = "+idCarrito+" AND IDPRODUCTO = "+idProducto);
		if(producto == null)producto = superandes.adicionarProductoACarrito(idCarrito, idProducto, 0);
		String sentenciaEstante = "SELECT idproducto, idalmacenamiento, cantidadproductos " + 
				"FROM (s_almacenamiento_productos INNER JOIN s_almacenamiento on idalmacenamiento = id) " + 
				"WHERE tipo = 'ESTANTE' AND idproducto = " + idProducto;
		AlmacenamientoProductos enEstante = (AlmacenamientoProductos)superandes.getElementCompleteSentence(AlmacenamientoProductos.class, sentenciaEstante);

		VOAlmacenamientoProductos ape = superandes.actualizarProductoAlmacenamiento(enEstante.getIdAlmacenamiento(), idProducto, cantidad, false);
		VOAlmacenamientoProductos apc = superandes.actualizarProductoAlmacenamiento(idCarrito, idProducto, cantidad, true);
		if(apc == null || ape == null) throw new Exception("Perdón, ocurrió un error sacando el producto");
		return producto;
	}
	
	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									REQ 14
	 ************************************************************************************************************************/
	/**
	 * retorna el producto de un carrito al estante dónde fue tomado 
	 * @param cantidad cantidad de productos a devolver
	 * @param idProducto producto a devolver
	 * @param idCarrito carrito del que se devuelve el producto
	 * @return el producto que fue devuelto
	 * @throws Exception en caso de que no exista el carrito o se den valores inválidos
	 */
	public AlmacenamientoProductos devolverProductos(int cantidad, long idProducto, long idCarrito)throws Exception {
		String sentenciaEstante = "SELECT idproducto, idalmacenamiento, cantidadproductos " + 
				"FROM (s_almacenamiento_productos INNER JOIN s_almacenamiento on idalmacenamiento = id) " + 
				"WHERE tipo = 'ESTANTE' AND idproducto = " + idProducto;
		AlmacenamientoProductos enEstante = (AlmacenamientoProductos)superandes.getElementCompleteSentence(AlmacenamientoProductos.class, sentenciaEstante);

		VOAlmacenamientoProductos apc = superandes.actualizarProductoAlmacenamiento(idCarrito, idProducto, cantidad, false);
		VOAlmacenamientoProductos ape = superandes.actualizarProductoAlmacenamiento(enEstante.getIdAlmacenamiento(), idProducto, cantidad, true);
		if(apc == null || ape == null) throw new Exception("Perdón, ocurrió un error sacando el producto");
		return enEstante;
	}

	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									REQ 15
	 ************************************************************************************************************************/
	/**
	 * genera una factura con los productos del carrito, luego cambia su estado y limpia el carrito
	 * @param c carrito a ser pagado
	 * @param sucursal sucursal del carrito
	 * @return el carrito que se pagó
	 * @throws Exception en caso de que el carrito no exista
	 */
	public Carrito facturarCarrito(Carrito c, long sucursal) throws Exception {
		superandes.facturarCarrito(c.getId_carrito(), sucursal, c.getDecliente());
		return desocuparCarrito(c);
	}


	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									REQ 16
	 ************************************************************************************************************************/
	/**
	 * cambia el estado de un carrito a abandonado, dejando los productos en su interior
	 * @param idCarrito carrito que será abandonado
	 * @return el carrito que fue abandonado
	 * @throws Exception en caso de que el carrito no exista o no se le pueda cambiar el estado
	 */
	public Carrito abandonarCarrito(long idCarrito) throws Exception{
		return cambiarEstadoDeCarrito(idCarrito, Carrito.ABANDONADO);
	}

	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									REQ 17
	 ************************************************************************************************************************/
	/**
	 * selecciona todos los carritos de la sucursal dad que posean un estado de abando nados y regresa todos sus productos a los estantes
	 * luego de la devolucion elimina el residuo para que no sea facturado en nuevo caso y actualiza el estado del carrito a disponible
	 * @param sucursal sucursal de los carritos que son recolectados
	 * @return un string con todos los carritos que fueron recolectados
	 * @throws Exception en caso de no haber carritos por estado abandonado o por inexistencia de los mismos
	 */
	public String recolectarCarritos(long sucursal) throws Exception{
		String sentencia = "SELECT ID, ESTADO, DECLIENTE FROM (S_ALMACENAMIENTO INNER JOIN S_CARRITO ON ID = ID_CARRITO) WHERE ESTADO = '"+Carrito.ABANDONADO+"' AND SUCURSAL = "+sucursal;
		String toRet = "";
		List<Object> carritos = superandes.getElementsCompleteSentence(Carrito.class, sentencia);
		if(carritos == null || carritos.isEmpty())throw new Exception("No hay carritos abandonados para recolectar");
		for (Object c : carritos) {
			toRet += "\n Recolectado el carrito: " + recolectarCarrito(((Carrito)c)).getId_carrito();
		}
		return toRet;
	}


	/** *********************************************************************************************************************/
	/* **********************************************************************************************************************
	 * 									MÉTODOS DE SOPORTE PARA LOS REQUERIMIENTOS
	 ************************************************************************************************************************/
	/**
	 * cambia el estado del carrito indicado a aquel que le es pasado por parámetro
	 * @param idCarrito carrito a cambiar
	 * @param nuevoEstado nuevo estado del carrito
	 * @return carrito al cual se le cambió el estado
	 * @throws Exception en caso de que el estado no pueda ser modificado
	 */
	private Carrito cambiarEstadoDeCarrito(long idCarrito, String nuevoEstado) throws Exception{
		Carrito cat = (Carrito)superandes.getElement(Carrito.class, "ID_CARRITO = "+idCarrito);
		if(cat == null || cat.estado.compareTo(Carrito.OCUPADO)== 0)throw new Exception("El carro está ocupado o no existe");
		return superandes.cambiarEstadoCarrito(idCarrito, nuevoEstado);
	}
	/**
	 * Devuelve todos los productos del carrito seleccionado a los estantes
	 * @param carrito
	 * @return
	 * @throws Exception
	 */
	public Carrito recolectarCarrito(Carrito carrito) throws Exception {
		Carrito ca = carrito;
		List<Object> prods = superandes.getElements(AlmacenamientoProductos.class, AlmacenamientoProductos.COL_IDALMACENAMIENTO+" = "+ca.getId_carrito());
		if(ca == null || prods == null) throw new Exception("Error en recolectar el carrito: "+ ca +
				" \n lista de productos válida: "+ (prods != null) + "\n carrito válido: "+ (ca!= null));
		for (Object p : prods) {
			AlmacenamientoProductos producto = (AlmacenamientoProductos)p;
			int max = producto.cantidadProductos();
			String sentenciaEstante = "SELECT idproducto, idalmacenamiento, cantidadproductos " + 
					"FROM (s_almacenamiento_productos INNER JOIN s_almacenamiento on idalmacenamiento = id) " + 
					"WHERE tipo = 'ESTANTE' AND idproducto = " + producto.getIdProducto();
			AlmacenamientoProductos enEstante = (AlmacenamientoProductos)superandes.getElementCompleteSentence(AlmacenamientoProductos.class, sentenciaEstante);

			VOAlmacenamientoProductos apc = superandes.actualizarProductoAlmacenamiento(ca.getId_carrito(), producto.getIdProducto(), max, false);
			VOAlmacenamientoProductos ape = superandes.actualizarProductoAlmacenamiento(enEstante.getIdAlmacenamiento(), enEstante.getIdProducto(), max, true);
			superandes.deleteElements(AlmacenamientoProductos.class, "IDPRODUCTO = "+ producto.getIdProducto() +" AND IDALMACENAMIENTO = " + producto.getIdAlmacenamiento()
			+" AND CANTIDADPRODUCTOS = " + 0);
			if(apc == null || ape == null) throw new Exception("Perdón, ocurrió un error sacando el producto");
		}
		superandes.cambiarEstadoCarrito(ca.getId_carrito(), Carrito.DISPONIBLE);
		return ca;
	}
	/**
	 * Devuelve todos los productos del carrito seleccionado a los estantes
	 * @param carrito
	 * @return
	 * @throws Exception
	 */
	public Carrito desocuparCarrito(Carrito carrito) throws Exception {
		Carrito ca = carrito;
		List<Object> prods = superandes.getElements(AlmacenamientoProductos.class, AlmacenamientoProductos.COL_IDALMACENAMIENTO+" = "+ca.getId_carrito());
		if(ca == null || prods == null) throw new Exception("Error en desocupar el carrito: "+ ca +
				" \n lista de productos válida: "+ (prods != null) + "\n carrito válido: "+ (ca!= null));
		for (Object p : prods) {
			AlmacenamientoProductos producto = (AlmacenamientoProductos)p;
			superandes.deleteElements(AlmacenamientoProductos.class, "IDPRODUCTO = "+ producto.getIdProducto() +" AND IDALMACENAMIENTO = " + producto.getIdAlmacenamiento());
		}
		superandes.cambiarEstadoCarrito(ca.getId_carrito(), Carrito.DISPONIBLE);
		return ca;
	}
	/**
	 * añade un nuevo carrito a la sucursal pasada por parámetro
	 * @param sucursal sucursal a la cual se le añadirá el carrito
	 * @return El VOCarrito correspondiente al carrito añadido
	 * @throws Exception en caso de que se rompa alguna regla del negocio
	 */
	public VOCarrito aniadirCarrito(long sucursal) throws Exception{
		VOAlmacenamiento va = superandes.adicionarAlmacenamiento(0, 1, 0, 1, Almacenamiento.TIPO_CARRITO, 0 /*Esta es la categoría carrito*/, 1, sucursal);
		if (va == null)
			throw new Exception ("No se pudo añadir el carrito");
		VOCarrito ca = superandes.aniadirCarrito(va.getId());
		if (ca == null)
			throw new Exception ("No se pudo añadir el carrito");
		return ca;
	}

}
