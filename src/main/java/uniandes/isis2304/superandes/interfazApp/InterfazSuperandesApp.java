package main.java.uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import main.java.uniandes.isis2304.superandes.negocio.Almacenamiento;
import main.java.uniandes.isis2304.superandes.negocio.AlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.Carrito;
import main.java.uniandes.isis2304.superandes.negocio.Cliente;
import main.java.uniandes.isis2304.superandes.negocio.RequerimientosIteracion2;
import main.java.uniandes.isis2304.superandes.negocio.Sucursal;
import main.java.uniandes.isis2304.superandes.negocio.SuperAndes;
import main.java.uniandes.isis2304.superandes.negocio.VOAlmacenamiento;
import main.java.uniandes.isis2304.superandes.negocio.VOAlmacenamientoProductos;
import main.java.uniandes.isis2304.superandes.negocio.VOCarrito;
import main.java.uniandes.isis2304.superandes.negocio.VOFactura;
import main.java.uniandes.isis2304.superandes.negocio.VOPedido;
import main.java.uniandes.isis2304.superandes.negocio.VOPromocion;

/**
 * Clase principal de la interfaz
 * @author Anderson Barragán */

@SuppressWarnings("serial") public class InterfazSuperandesApp extends JFrame implements ActionListener{

	/* **************************************************************************************************************************
	 * 													Constantes
	 ****************************************************************************************************************************/
	/** Logger para escribir la traza de la ejecución */
	private static Logger log = Logger.getLogger(InterfazSuperandesApp.class.getName());

	/** Ruta al archivo de configuración de la interfaz */
	private static final String CONFIG_INTERFAZ = "./resources/config/interfaceConfigApp.json";

	/** Ruta al archivo de configuración de los nombres de tablas de la base de datos */
	private static final String CONFIG_TABLAS = "./resources/config/TablasBD_A.json"; 

	/* **************************************************************************************************************************
	 * 													Atributos
	 ****************************************************************************************************************************/
	/** Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar */
	private JsonObject tableConfig;

	/** Asociación a la clase principal del negocio. */
	private SuperAndes superandes;

	private RequerimientosIteracion2 iteracion2;

	private Sucursal currentSucursal;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/** Objeto JSON con la configuración de interfaz de la app.*/
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/** Menú de la aplicación */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazSuperandesApp( )
	{

		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{	
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		superandes = new SuperAndes (tableConfig);
		iteracion2 = new RequerimientosIteracion2(superandes);

		String idt = JOptionPane.showInputDialog (this, "a que sucursal pertenece", "añadir", JOptionPane.QUESTION_MESSAGE);
		this.currentSucursal = (Sucursal)superandes.getElement(Sucursal.class, "IDSUCURSAL = "+idt);
		if(currentSucursal == null)currentSucursal =(Sucursal)superandes.getElement(Sucursal.class, "NOMBRE = '"+ idt+"'");
		if(this.currentSucursal == null || idt == null || idt.equals("")) {
			JOptionPane.showMessageDialog(this, "Error con la sucursal seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/** Método para configurar el frame principal de la aplicación */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Superandes APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base en el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menús deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/* ****************************************************************
	 * 		METODOS DE LOS REQUERIMIENTOS FUNCIONALES ITERACIÓN 1
	 *****************************************************************/

	/**
	 * Adiciona una promoción con la información dada por el usuario
	 * Se crea una nueva tupla de tipoBebida en la base de datos, si la descripción de 
	 * la promoción y el producto no son iguales a una tupla existente
	 */
	public void adicionarPromocion( )	{
		try 		{

			String descripcion = JOptionPane.showInputDialog (this, "descripción de la promo?", "aniadir descripcion", JOptionPane.QUESTION_MESSAGE);
			String fechafin = JOptionPane.showInputDialog (this, "fin de la promo?", "aniadir fecha", JOptionPane.QUESTION_MESSAGE);
			String cantProductos = JOptionPane.showInputDialog (this, "cuantosProductosParaLaPromo?", "aniadir cantidad", JOptionPane.QUESTION_MESSAGE);

			if (descripcion != null && fechafin != null && cantProductos != null)	{
				VOPromocion tb = superandes.adicionarPromocion(descripcion, fechafin, Integer.parseInt(cantProductos));
				if (tb == null)
					throw new Exception ("No se pudo crear la promo con descripcion: " + descripcion);

				panelDatos.actualizarInterfaz("En adicionarPromocion\n\npromocion añadida exitosamente: " + tb + "\n Operación terminada");
			}else panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		} catch (Exception e) {
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}

	/**
	 * Finaliza - borra de la base de datos la promoción con la descripción dada por el usuario
	 * Cuando dicha promoción no existe, se indica que se borraron 0 registros de la base de datos
	 */
	public void finalizarPromoPorDescripcion( )	{
		try 		{
			String idTipoStr = JOptionPane.showInputDialog (this, "descripcion de la promo?", "Finalizar promocion con descripcion", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null)
			{					
				long tbEliminados = superandes.finalizarPromoPorDescripcion(idTipoStr);

				String resultado = "En finalizar Promocion\n\n";
				resultado += tbEliminados + " promociones eliminadas\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos la promoción con el identificador dado por el usuario
	 * Cuando dicha promoción no existe, se indica que se borraron 0 registros de la base de datos
	 */
	public void finalizarPromoPorId( )	{
		try 		{
			String idTipoStr = JOptionPane.showInputDialog (this, "Id de la promo?", "Finalizar promocion con id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null)
			{					
				long idTipo = Long.valueOf (idTipoStr);
				long tbEliminados = superandes.finalizarPromoPorId (idTipo);

				String resultado = "En finalizar Promocion\n\n";
				resultado += tbEliminados + " promociones eliminadas\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * registra un pedido en la base de datos
	 * si el producto del pedido y el proveedor del pedido coinciden con una tupla ya existente,
	 * se indica que se agregaron 0 registros a la base de datos
	 */
	public void registrarPedido( )	{
		try 		{
			String sucursalr =JOptionPane.showInputDialog (this, "que sucursal?", "esta sucrsal", JOptionPane.QUESTION_MESSAGE);
			double precioPedido = Double.parseDouble(JOptionPane.showInputDialog (this, "cuanto es el pedido?", "ajustar precio", JOptionPane.QUESTION_MESSAGE));
			String fechaAcordada =JOptionPane.showInputDialog (this, "para cuando?", "ajustar fecha", JOptionPane.QUESTION_MESSAGE);
			String proveedorr =JOptionPane.showInputDialog (this, "id del proveedor?", "ajustar proveedor", JOptionPane.QUESTION_MESSAGE);
			long idTipo = Long.valueOf (proveedorr);
			long sucursal= Long.valueOf(sucursalr);

			if (fechaAcordada != null && proveedorr != null && sucursalr != null)	{

				//evaluacion inicia en cero porque no se ha recibido el pedido
				VOPedido tb = superandes.registrarPedido(precioPedido, fechaAcordada, idTipo, sucursal);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear el pedido entre: " + idTipo + "-" + sucursal);
				}
				String resultado = "En registrarPedido\n\n";
				resultado += "registro añadido exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}else
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		} 
		catch (Exception e) {
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * registra un producto en la base de datos
	 */
	public void registrarProducto( )	{
		try 		{
			String sucursalr =JOptionPane.showInputDialog (this, "que sucursal?", "esta sucrsal", JOptionPane.QUESTION_MESSAGE);
			double precioPedido = Double.parseDouble(JOptionPane.showInputDialog (this, "cuanto es el pedido?", "ajustar precio", JOptionPane.QUESTION_MESSAGE));
			String fechaAcordada =JOptionPane.showInputDialog (this, "para cuando?", "ajustar fecha", JOptionPane.QUESTION_MESSAGE);
			String proveedorr =JOptionPane.showInputDialog (this, "id del proveedor?", "ajustar proveedor", JOptionPane.QUESTION_MESSAGE);
			long idTipo = Long.valueOf (proveedorr);
			long sucursal= Long.valueOf(sucursalr);

			if (fechaAcordada != null && proveedorr != null && sucursalr != null)	{

				//evaluacion inicia en cero porque no se ha recibido el pedido
				VOPedido tb = superandes.registrarPedido(precioPedido, fechaAcordada, idTipo, sucursal);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear el pedido entre: " + idTipo + "-" + sucursal);
				}
				String resultado = "En registrarPedido\n\n";
				resultado += "registro añadido exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}else
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		} 
		catch (Exception e) {
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/** registra una nueva factura en la base de datos */
	public void registrarFactura( )	{
		try 		{
			String fechaFactura =JOptionPane.showInputDialog (this, "Cuando fue?", "ajustar fecha", JOptionPane.QUESTION_MESSAGE);
			String idSucursalr =JOptionPane.showInputDialog (this, "en donde?", "ajustar lugar", JOptionPane.QUESTION_MESSAGE);
			String idClienter =JOptionPane.showInputDialog (this, "id del client?", "ajustar cliente", JOptionPane.QUESTION_MESSAGE);
			long idSucursal = Long.valueOf (idSucursalr);
			long idCliente= Long.valueOf(idClienter);

			if (fechaFactura != null && idSucursalr != null)	{

				//evaluacion inicia en cero porque no se ha recibido el pedido
				VOFactura tb = superandes.registrarFactura(fechaFactura, idSucursal, idCliente);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear la factura de en: " + idCliente + "-" + idSucursal);
				}
				String resultado = "En registrarVenta\n\n";
				resultado += "Factura añadida exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}else
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		} 
		catch (Exception e) {
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//________________________________________________ITERACIÓN 2___________________________________________________________________________
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/*working */
	public void solicitarCarrito( ) {
		try {
			int tomar = JOptionPane.showConfirmDialog(this, "¿sabes el código del carrito?");
			Carrito ca = null;
			if ( tomar == 1)	{
				List<Carrito> vas = superandes.listarCarritosDisponibles(this.currentSucursal.getId());
				long doc = Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE));
				ca = iteracion2.solicitarCarrito(doc,vas,this.currentSucursal.getId());
				panelDatos.actualizarInterfaz("El carrito\n\ncarrito añadido exitosamente: " + ca + "\n Operación terminada");
			}
			else if ( tomar == 0)	{
				long idCarrito = Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa el codigo del carrito", "aceptar", JOptionPane.QUESTION_MESSAGE));
				ca = iteracion2.solicitarCarrito(idCarrito, this.currentSucursal.getId(),
						Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE)));
				panelDatos.actualizarInterfaz("El carrito\n\ncarrito añadido exitosamente: " + ca + "\n Operación terminada");
			}else panelDatos.actualizarInterfaz("Operación cancelada por el usuario");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void aniadirCarrito( ) {
		try {
			int aniadir = JOptionPane.showConfirmDialog(this, "¿desea añadir un nuevo carrito?");
			if(aniadir == 0)
				//-------------------------------------------------------------------------------------------método de la iteración
				panelDatos.actualizarInterfaz("Añadir carrito\n\ncarrito añadido exitosamente: " + (Carrito) iteracion2.aniadirCarrito(this.currentSucursal.getId()) + "\n Operación terminada");
			else panelDatos.actualizarInterfaz("operacion calcelada por el usuario");
		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void aniadirProducto( ) {
		try {
			int sabes  = JOptionPane.showConfirmDialog(this, "¿conoces el id del carrito?");
			String[] datos = null;
			long idCarrito = 0, idProducto = 0;	int cantidad = 0;
			if(sabes == 0) {
				datos = dataRequest(new String[] {"el id del carrito", "el id del producto","cuantos productos"});
				idCarrito  = Long.valueOf(datos[0]);
			}
			else if(sabes == 1) {
				datos = dataRequest(new String[] {"su documento", "el id del producto","cuantos productos"});
				Carrito ca = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+datos[0]);
				idCarrito = ca.getId_carrito();
			}
			else throw new Exception("operacion calcelada por el usuario");
			idProducto = Long.valueOf(datos[1]);
			cantidad = Integer.parseInt(datos[2]);

			//método de la iteración
			AlmacenamientoProductos producto = iteracion2.aniadirProducto(idCarrito, idProducto, cantidad);

			panelDatos.actualizarInterfaz("Añadir producto\n\nproducto añadido exitosamente: " + producto + "\n Operación terminada");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void devolverProducto( ) {
		try {
			try {
				int sabes  = JOptionPane.showConfirmDialog(this, "¿conoces el id del carrito?");
				String[] datos = null;
				long idCarrito = 0, idProducto = 0;	int cantidad = -1;
				if(sabes == 0) {
					datos = dataRequest(new String[] {"el id del carrito", "el id del producto"});
					idCarrito  = Long.valueOf(datos[0]);
				}
				else if(sabes == 1) {
					datos = dataRequest(new String[] {"su documento", "el id del producto"});
					Carrito ca = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+datos[0]);
					idCarrito = ca.getDecliente();
				}
				else throw new Exception("operacion calcelada por el usuario");

				idProducto = Long.valueOf(datos[1]);
				AlmacenamientoProductos producto = (AlmacenamientoProductos)superandes.getElement(AlmacenamientoProductos.class, "IDALMACENAMIENTO = "+idCarrito+" AND IDPRODUCTO = "+idProducto);
				if(producto == null)throw new Exception("El id seleccionado es erróneo");
				int max = producto.cantidadProductos();
				cantidad = (JOptionPane.showConfirmDialog(this, "¿todos?") == 0)?max:Integer.parseInt(JOptionPane.showInputDialog (this, "cuántos: ", "Cantidad ", JOptionPane.QUESTION_MESSAGE));	
				if(0 > cantidad||cantidad > max)throw new Exception("Lo lamento, la cantidad que escogiste no es posible");

				//método de la iteración
				AlmacenamientoProductos p = iteracion2.devolverProductos(cantidad, idProducto, idCarrito);

				panelDatos.actualizarInterfaz("Se obtuvo: "+ p.cantidadProductos()+ p.getIdAlmacenamiento() +" y "+p.getIdProducto()+ "\n Operación terminada");

			} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	public void pagarCarrito( ) {
		try {
			String temp = JOptionPane.showInputDialog (this, "Documento: ", "Por favor ingresa el Documento", JOptionPane.QUESTION_MESSAGE);
			if(temp == null)throw new Exception("Lo lamento, documento no legible");
			Carrito ca = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+temp);
			if(ca == null || ca.getDecliente() == 0)throw new Exception("Al parecer el cliente no tiene un carrito");
			iteracion2.facturarCarrito(ca, this.currentSucursal.getId());
			panelDatos.actualizarInterfaz("Se pagó el carrito: "+ca.getId_carrito() +" asociado al cliente: "+ ca.getDecliente() );
		}catch(Exception e) {
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}

	/* working	*/
	public void abandonarCarrito( ) {
		try {
			int abandonar = JOptionPane.showConfirmDialog(this, "¿sabes el código del carrito?");
			long idCarrito = -1;
			Carrito ca = null;
			if ( abandonar == 1)	{
				String documento = JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE);
				if(documento == null)throw new Exception("Error: documento inválido");

				Carrito Dtemp = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+documento);
				if(Dtemp == null || !Dtemp.getEstado().equals(Carrito.OCUPADO))throw new Exception("El carrito está libre o ya está abandonado");
				idCarrito = Dtemp.getId_carrito();
			}
			else if ( abandonar == 0)
				idCarrito = Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa el codigo del carrito", "aceptar", JOptionPane.QUESTION_MESSAGE));
			else throw new Exception("Operación cancelada por el usuario");

			//método de la iteración
			ca = iteracion2.abandonarCarrito(idCarrito);
			panelDatos.actualizarInterfaz("El carrito\n\ncarrito añadido exitosamente: " + ca + "\n Operación terminada");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void abandonarCarritoBien( ) {
		try {
			int abandonar = JOptionPane.showConfirmDialog(this, "¿sabes el código del carrito?");
			long idCarrito = -1;
			if ( abandonar == 1)	{
				String documento = JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE);
				if(documento == null)throw new Exception("Error: documento inválido");

				Carrito Dtemp = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+documento);
				if(Dtemp == null || !Dtemp.getEstado().equals(Carrito.OCUPADO))throw new Exception("El carrito está libre o ya está abandonado");
				idCarrito = Dtemp.getId_carrito();
			}
			else if ( abandonar == 0)	{
				String idCarritoS = JOptionPane.showInputDialog (this, "Por favor ingresa el codigo del carrito", "aceptar", JOptionPane.QUESTION_MESSAGE);
				idCarrito = Long.valueOf(idCarritoS);
			}else throw new Exception("Operación cancelada por el usuario");

			Carrito ca = superandes.cambiarEstadoCarrito(idCarrito, Carrito.ABANDONADO);
			iteracion2.recolectarCarrito(ca);
			panelDatos.actualizarInterfaz("El carrito\n\ncarrito añadido exitosamente: " + ca + "\n Operación terminada");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/*working*/
	public void recolectarCarritos( ) {
		try {
			int seguro = JOptionPane.showConfirmDialog(this, "¿desea recolectar todos los carritos?");
			if(seguro == 0)
				panelDatos.actualizarInterfaz(iteracion2.recolectarCarritos(this.currentSucursal.getId()));
			else throw new Exception("Operación cancelada");
		}catch(Exception e) {panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	public void PRUEBA() {

	}public void nothign() {

	}
	/* ****************************************************************
	 * 		REQUERIMIENTOS FUNCIONALES DE CONSULTA ITERACION 1
	 ******************************************************************/

	public void RFC1() {
		String fechaFactura =JOptionPane.showInputDialog (this, "por favor añade una fecha", "ajustar fecha", JOptionPane.QUESTION_MESSAGE);
		if(fechaFactura != null ) {
			/* se supone que en vez de string se coloque la clase (Vo) sobre la que se actua */
			String elementoVOdeLaClaseAmanejar = superandes.RFC1(fechaFactura);
			/* aquí se crea una cadena en la que se decribe el objeto para actualizar la interfaz y ese string se le pasa a PanelDatos */
			panelDatos.actualizarInterfaz(elementoVOdeLaClaseAmanejar);
		}else
			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
	}

	public void RFC7() {
		String toMyPane = "";
		int rango = Integer.parseInt(JOptionPane.showInputDialog( this, "Qué rango", "Rango", JOptionPane.QUESTION_MESSAGE));	
		long idProducto = Long.valueOf(JOptionPane.showInputDialog( this, "Qué producto", "Producto", JOptionPane.QUESTION_MESSAGE));
		List<String[]> toRet = superandes.analizarOperacionSuperAndes(idProducto, rango);
		for (String[] s : toRet) {
			toMyPane += "\n \t";
			toMyPane += "en la sucursal: "+s[0]+" con "+s[1]+" entre "+s[2]+"  "+s[3];
		}
		panelDatos.actualizarInterfaz(toMyPane);
	}
	public void RFC8() {
		List<Cliente> frecuentes = superandes.clientesFrecuentes(this.currentSucursal.getId());
		String toRet = "";
		for (Cliente c : frecuentes) {
			toRet += "\n\t"+c;
		}
		panelDatos.actualizarInterfaz(toRet);
	}
	//_____________________________________________________________________________________________________________________________________
	//_____________________________________________________________________________________________________________________________________

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/** Muestra el log de superandes */
	public void mostrarLogSuperandes ()	{
		mostrarArchivo ("SuperAndes.log");
	}

	/** Muestra el log de datanucleus */
	public void mostrarLogDatanuecleus ()	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de superandes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogSuperandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("SuperAndes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de Superandes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de superandes
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecución de la demo y recolección de los resultados
			long eliminados [] = superandes.limpiarSuperandes();

			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Almacenamientos eliminados\n";
			resultado += eliminados [1] + " AlmacenamientoProductos eliminados\n";
			resultado += eliminados [2] + " Categorias eliminadas\n";
			resultado += eliminados [3] + " Clientes eliminados\n";
			resultado += eliminados [4] + " Facturas eliminadas\n";
			resultado += eliminados [5] + " Pedidos eliminados\n";
			resultado += eliminados [6] + " Productos eliminados\n";
			resultado += eliminados [7] + " Productos de catedgorias eliminados\n";
			resultado += eliminados [8] + " Productos de proveedor eliminados\n";
			resultado += eliminados [9] + " Productos de Facturas eliminados\n";
			resultado += eliminados [10] + " Productos de pedidos eliminados\n";
			resultado += eliminados [11] + " Productos de sucursal eliminados\n";
			resultado += eliminados [12] + " Promociones eliminadas\n";
			resultado += eliminados [13] + " Promociones asociadas eliminadas\n";
			resultado += eliminados [14] + " Proveedores eliminadoss\n";
			resultado += eliminados [15] + " Proveen eliminados\n";
			resultado += eliminados [16] + " Sucursales eliminadas\n";
			resultado += "\n\tLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/** Muestra el modelo conceptual de Superandes */
	public void mostrarModeloConceptual ()	{
		mostrarArchivo ("data/ModeloConceptualSuperandes.pdf");
	}

	/** Muestra el esquema de la base de datos de Superandes */
	public void mostrarEsquemaBD ()	{
		mostrarArchivo ("data/Esquema BD SuperAndes.pdf");
	}

	/** Muestra el script de creación de la base de datos */
	public void mostrarScriptBD ()	{
		mostrarArchivo ("data/EsquemaSuperandes.sql");
	}

	/** Muestra la documentación Javadoc del proyecto */
	public void mostrarJavadoc ()	{
		mostrarArchivo ("doc/index.html");
	}

	/** Muestra la información acerca del desarrollo de esta aplicación */
	public void acercaDe ()	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: SuperAndes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Anderson Barragán && Sebastian Mujica\n";
		resultado += " * Octubre de 2018\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}

	/* *********************************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 ***********************************************************************************/
	/**
	 * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
	 * @param lista - La lista con los tipos de bebida
	 * @return La cadena con una línea para cada tipo de bebida recibido
	 */
	private String listarElementos(List<?> lista, String tipoAListar) {
		if(lista == null)return "Error en la lista de carritos";
		String resp = "Los "+" existentes son existentes son:\n";
		int i = 1;
		for (Object al : lista)
		{
			resp += i++ + ". " + al.toString() + "\n";
		}
		return resp;
	}

	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepciones de JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y SuperAndes.log para más detalles";
		return resultado;
	}

	private String[] dataRequest(String[] preguntas) throws Exception {
		String[] resultados = new String[preguntas.length];
		for (int i = 0; i < preguntas.length; i++) {
			String temp = JOptionPane.showInputDialog (this, preguntas[i]+": ", "Por favor ingresa " + preguntas[i], JOptionPane.QUESTION_MESSAGE);
			if(temp == null || temp.compareTo("") == 0)throw new Exception("Por favor ingresa un dato!");
			resultados[i] = temp;
		}
		return resultados;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */

	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 	{
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)	{
		try	{
			Desktop.getDesktop().open(new File(nombreArchivo));

		}catch (IOException e)	{	e.printStackTrace( );	}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		//recive el nombre del evento que fue previamente definido en el addActionCommand
		String evento = pEvento.getActionCommand( ); 
		log.info ("		>>>>>>>>>>>>Se llama al metodo:		-" + evento);
		try	{
			//Crea un objeto de la clase Method (o sea un método) definido por la clase que contiene un método con nombre determinado
			Method req = InterfazSuperandesApp.class.getMethod ( evento );	
			//invoca el método (lo ejecuta) en la instancia de la clase que se le pasa por parámetro
			req.invoke ( this );
		} 	catch (Exception e) {//si el método no existe, supongo
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args ){
		try{
			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazSuperandesApp interfaz = new InterfazSuperandesApp( );
			interfaz.setVisible( true );

		}catch( Exception e ){	e.printStackTrace( );	}
	}
}
