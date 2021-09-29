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
 * @author Anderson Barrag�n */

@SuppressWarnings("serial") public class InterfazSuperandesApp extends JFrame implements ActionListener{

	/* **************************************************************************************************************************
	 * 													Constantes
	 ****************************************************************************************************************************/
	/** Logger para escribir la traza de la ejecuci�n */
	private static Logger log = Logger.getLogger(InterfazSuperandesApp.class.getName());

	/** Ruta al archivo de configuraci�n de la interfaz */
	private static final String CONFIG_INTERFAZ = "./resources/config/interfaceConfigApp.json";

	/** Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos */
	private static final String CONFIG_TABLAS = "./resources/config/TablasBD_A.json"; 

	/* **************************************************************************************************************************
	 * 													Atributos
	 ****************************************************************************************************************************/
	/** Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar */
	private JsonObject tableConfig;

	/** Asociaci�n a la clase principal del negocio. */
	private SuperAndes superandes;

	private RequerimientosIteracion2 iteracion2;

	private Sucursal currentSucursal;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/** Objeto JSON con la configuraci�n de interfaz de la app.*/
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacci�n para los requerimientos
	 */
	private PanelDatos panelDatos;

	/** Men� de la aplicaci�n */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicaci�n. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazSuperandesApp( )
	{

		// Carga la configuraci�n de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gr�fica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{	
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		superandes = new SuperAndes (tableConfig);
		iteracion2 = new RequerimientosIteracion2(superandes);

		String idt = JOptionPane.showInputDialog (this, "a que sucursal pertenece", "a�adir", JOptionPane.QUESTION_MESSAGE);
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
	 * 			M�todos de configuraci�n de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuraci�n deseada
	 * @param archConfig - Archivo Json que contiene la configuraci�n
	 * @return Un objeto JSON con la configuraci�n del tipo especificado
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
			log.info ("Se encontr� un archivo de configuraci�n v�lido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de interfaz v�lido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/** M�todo para configurar el frame principal de la aplicaci�n */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuraci�n por defecto" );			
			titulo = "Superandes APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuraci�n indicada en el archivo de configuraci�n" );
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
	 * M�todo para crear el men� de la aplicaci�n con base en el objeto JSON le�do
	 * Genera una barra de men� y los men�s con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los men�s deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creaci�n de la barra de men�s
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creaci�n de cada uno de los men�s
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creaci�n de cada una de las opciones del men�
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
	 * 		METODOS DE LOS REQUERIMIENTOS FUNCIONALES ITERACI�N 1
	 *****************************************************************/

	/**
	 * Adiciona una promoci�n con la informaci�n dada por el usuario
	 * Se crea una nueva tupla de tipoBebida en la base de datos, si la descripci�n de 
	 * la promoci�n y el producto no son iguales a una tupla existente
	 */
	public void adicionarPromocion( )	{
		try 		{

			String descripcion = JOptionPane.showInputDialog (this, "descripci�n de la promo?", "aniadir descripcion", JOptionPane.QUESTION_MESSAGE);
			String fechafin = JOptionPane.showInputDialog (this, "fin de la promo?", "aniadir fecha", JOptionPane.QUESTION_MESSAGE);
			String cantProductos = JOptionPane.showInputDialog (this, "cuantosProductosParaLaPromo?", "aniadir cantidad", JOptionPane.QUESTION_MESSAGE);

			if (descripcion != null && fechafin != null && cantProductos != null)	{
				VOPromocion tb = superandes.adicionarPromocion(descripcion, fechafin, Integer.parseInt(cantProductos));
				if (tb == null)
					throw new Exception ("No se pudo crear la promo con descripcion: " + descripcion);

				panelDatos.actualizarInterfaz("En adicionarPromocion\n\npromocion a�adida exitosamente: " + tb + "\n Operaci�n terminada");
			}else panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
		} catch (Exception e) {
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}

	/**
	 * Finaliza - borra de la base de datos la promoci�n con la descripci�n dada por el usuario
	 * Cuando dicha promoci�n no existe, se indica que se borraron 0 registros de la base de datos
	 */
	public void finalizarPromoPorDescripcion( )	{
		try 		{
			String idTipoStr = JOptionPane.showInputDialog (this, "descripcion de la promo?", "Finalizar promocion con descripcion", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null)
			{					
				long tbEliminados = superandes.finalizarPromoPorDescripcion(idTipoStr);

				String resultado = "En finalizar Promocion\n\n";
				resultado += tbEliminados + " promociones eliminadas\n";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
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
	 * Borra de la base de datos la promoci�n con el identificador dado por el usuario
	 * Cuando dicha promoci�n no existe, se indica que se borraron 0 registros de la base de datos
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
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
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
				resultado += "registro a�adido exitosamente: " + tb;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}else
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
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
				resultado += "registro a�adido exitosamente: " + tb;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}else
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
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
				resultado += "Factura a�adida exitosamente: " + tb;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}else
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
		} 
		catch (Exception e) {
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//________________________________________________ITERACI�N 2___________________________________________________________________________
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/*working */
	public void solicitarCarrito( ) {
		try {
			int tomar = JOptionPane.showConfirmDialog(this, "�sabes el c�digo del carrito?");
			Carrito ca = null;
			if ( tomar == 1)	{
				List<Carrito> vas = superandes.listarCarritosDisponibles(this.currentSucursal.getId());
				long doc = Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE));
				ca = iteracion2.solicitarCarrito(doc,vas,this.currentSucursal.getId());
				panelDatos.actualizarInterfaz("El carrito\n\ncarrito a�adido exitosamente: " + ca + "\n Operaci�n terminada");
			}
			else if ( tomar == 0)	{
				long idCarrito = Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa el codigo del carrito", "aceptar", JOptionPane.QUESTION_MESSAGE));
				ca = iteracion2.solicitarCarrito(idCarrito, this.currentSucursal.getId(),
						Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE)));
				panelDatos.actualizarInterfaz("El carrito\n\ncarrito a�adido exitosamente: " + ca + "\n Operaci�n terminada");
			}else panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void aniadirCarrito( ) {
		try {
			int aniadir = JOptionPane.showConfirmDialog(this, "�desea a�adir un nuevo carrito?");
			if(aniadir == 0)
				//-------------------------------------------------------------------------------------------m�todo de la iteraci�n
				panelDatos.actualizarInterfaz("A�adir carrito\n\ncarrito a�adido exitosamente: " + (Carrito) iteracion2.aniadirCarrito(this.currentSucursal.getId()) + "\n Operaci�n terminada");
			else panelDatos.actualizarInterfaz("operacion calcelada por el usuario");
		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void aniadirProducto( ) {
		try {
			int sabes  = JOptionPane.showConfirmDialog(this, "�conoces el id del carrito?");
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

			//m�todo de la iteraci�n
			AlmacenamientoProductos producto = iteracion2.aniadirProducto(idCarrito, idProducto, cantidad);

			panelDatos.actualizarInterfaz("A�adir producto\n\nproducto a�adido exitosamente: " + producto + "\n Operaci�n terminada");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void devolverProducto( ) {
		try {
			try {
				int sabes  = JOptionPane.showConfirmDialog(this, "�conoces el id del carrito?");
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
				if(producto == null)throw new Exception("El id seleccionado es err�neo");
				int max = producto.cantidadProductos();
				cantidad = (JOptionPane.showConfirmDialog(this, "�todos?") == 0)?max:Integer.parseInt(JOptionPane.showInputDialog (this, "cu�ntos: ", "Cantidad ", JOptionPane.QUESTION_MESSAGE));	
				if(0 > cantidad||cantidad > max)throw new Exception("Lo lamento, la cantidad que escogiste no es posible");

				//m�todo de la iteraci�n
				AlmacenamientoProductos p = iteracion2.devolverProductos(cantidad, idProducto, idCarrito);

				panelDatos.actualizarInterfaz("Se obtuvo: "+ p.cantidadProductos()+ p.getIdAlmacenamiento() +" y "+p.getIdProducto()+ "\n Operaci�n terminada");

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
			panelDatos.actualizarInterfaz("Se pag� el carrito: "+ca.getId_carrito() +" asociado al cliente: "+ ca.getDecliente() );
		}catch(Exception e) {
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}

	/* working	*/
	public void abandonarCarrito( ) {
		try {
			int abandonar = JOptionPane.showConfirmDialog(this, "�sabes el c�digo del carrito?");
			long idCarrito = -1;
			Carrito ca = null;
			if ( abandonar == 1)	{
				String documento = JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE);
				if(documento == null)throw new Exception("Error: documento inv�lido");

				Carrito Dtemp = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+documento);
				if(Dtemp == null || !Dtemp.getEstado().equals(Carrito.OCUPADO))throw new Exception("El carrito est� libre o ya est� abandonado");
				idCarrito = Dtemp.getId_carrito();
			}
			else if ( abandonar == 0)
				idCarrito = Long.valueOf(JOptionPane.showInputDialog (this, "Por favor ingresa el codigo del carrito", "aceptar", JOptionPane.QUESTION_MESSAGE));
			else throw new Exception("Operaci�n cancelada por el usuario");

			//m�todo de la iteraci�n
			ca = iteracion2.abandonarCarrito(idCarrito);
			panelDatos.actualizarInterfaz("El carrito\n\ncarrito a�adido exitosamente: " + ca + "\n Operaci�n terminada");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/* working */
	public void abandonarCarritoBien( ) {
		try {
			int abandonar = JOptionPane.showConfirmDialog(this, "�sabes el c�digo del carrito?");
			long idCarrito = -1;
			if ( abandonar == 1)	{
				String documento = JOptionPane.showInputDialog (this, "Por favor ingresa tu documento", "aceptar", JOptionPane.QUESTION_MESSAGE);
				if(documento == null)throw new Exception("Error: documento inv�lido");

				Carrito Dtemp = (Carrito)superandes.getElement(Carrito.class, "DECLIENTE = "+documento);
				if(Dtemp == null || !Dtemp.getEstado().equals(Carrito.OCUPADO))throw new Exception("El carrito est� libre o ya est� abandonado");
				idCarrito = Dtemp.getId_carrito();
			}
			else if ( abandonar == 0)	{
				String idCarritoS = JOptionPane.showInputDialog (this, "Por favor ingresa el codigo del carrito", "aceptar", JOptionPane.QUESTION_MESSAGE);
				idCarrito = Long.valueOf(idCarritoS);
			}else throw new Exception("Operaci�n cancelada por el usuario");

			Carrito ca = superandes.cambiarEstadoCarrito(idCarrito, Carrito.ABANDONADO);
			iteracion2.recolectarCarrito(ca);
			panelDatos.actualizarInterfaz("El carrito\n\ncarrito a�adido exitosamente: " + ca + "\n Operaci�n terminada");

		} catch (Exception e) { panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	/*working*/
	public void recolectarCarritos( ) {
		try {
			int seguro = JOptionPane.showConfirmDialog(this, "�desea recolectar todos los carritos?");
			if(seguro == 0)
				panelDatos.actualizarInterfaz(iteracion2.recolectarCarritos(this.currentSucursal.getId()));
			else throw new Exception("Operaci�n cancelada");
		}catch(Exception e) {panelDatos.actualizarInterfaz(generarMensajeError(e)); }
	}

	public void PRUEBA() {

	}public void nothign() {

	}
	/* ****************************************************************
	 * 		REQUERIMIENTOS FUNCIONALES DE CONSULTA ITERACION 1
	 ******************************************************************/

	public void RFC1() {
		String fechaFactura =JOptionPane.showInputDialog (this, "por favor a�ade una fecha", "ajustar fecha", JOptionPane.QUESTION_MESSAGE);
		if(fechaFactura != null ) {
			/* se supone que en vez de string se coloque la clase (Vo) sobre la que se actua */
			String elementoVOdeLaClaseAmanejar = superandes.RFC1(fechaFactura);
			/* aqu� se crea una cadena en la que se decribe el objeto para actualizar la interfaz y ese string se le pasa a PanelDatos */
			panelDatos.actualizarInterfaz(elementoVOdeLaClaseAmanejar);
		}else
			panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
	}

	public void RFC7() {
		String toMyPane = "";
		int rango = Integer.parseInt(JOptionPane.showInputDialog( this, "Qu� rango", "Rango", JOptionPane.QUESTION_MESSAGE));	
		long idProducto = Long.valueOf(JOptionPane.showInputDialog( this, "Qu� producto", "Producto", JOptionPane.QUESTION_MESSAGE));
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
	 * 			M�todos administrativos
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
	 * Muestra en el panel de datos la traza de la ejecuci�n
	 */
	public void limpiarLogSuperandes ()
	{
		// Ejecuci�n de la operaci�n y recolecci�n de los resultados
		boolean resp = limpiarArchivo ("SuperAndes.log");

		// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
		String resultado = "\n\n************ Limpiando el log de Superandes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecuci�n
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecuci�n de la operaci�n y recolecci�n de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de superandes
	 * Muestra en el panel de datos el n�mero de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecuci�n de la demo y recolecci�n de los resultados
			long eliminados [] = superandes.limpiarSuperandes();

			// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
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

	/** Muestra el script de creaci�n de la base de datos */
	public void mostrarScriptBD ()	{
		mostrarArchivo ("data/EsquemaSuperandes.sql");
	}

	/** Muestra la documentaci�n Javadoc del proyecto */
	public void mostrarJavadoc ()	{
		mostrarArchivo ("doc/index.html");
	}

	/** Muestra la informaci�n acerca del desarrollo de esta aplicaci�n */
	public void acercaDe ()	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogot�	- Colombia)\n";
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: SuperAndes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Anderson Barrag�n && Sebastian Mujica\n";
		resultado += " * Octubre de 2018\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}

	/* *********************************************************************************
	 * 			M�todos privados para la presentaci�n de resultados y otras operaciones
	 ***********************************************************************************/
	/**
	 * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una l�nea por cada tipo de bebida
	 * @param lista - La lista con los tipos de bebida
	 * @return La cadena con una l�nea para cada tipo de bebida recibido
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
	 * Genera una cadena de caracteres con la descripci�n de la excepcion e, haciendo �nfasis en las excepciones de JDO
	 * @param e - La excepci�n recibida
	 * @return La descripci�n de la excepci�n, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicaci�n
	 * @param e - La excepci�n generada
	 * @return La cadena con la informaci�n de la excepci�n y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecuci�n\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y SuperAndes.log para m�s detalles";
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
	 * Abre el archivo dado como par�metro con la aplicaci�n por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)	{
		try	{
			Desktop.getDesktop().open(new File(nombreArchivo));

		}catch (IOException e)	{	e.printStackTrace( );	}
	}

	/* ****************************************************************
	 * 			M�todos de la Interacci�n
	 *****************************************************************/
	/**
	 * M�todo para la ejecuci�n de los eventos que enlazan el men� con los m�todos de negocio
	 * Invoca al m�todo correspondiente seg�n el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		//recive el nombre del evento que fue previamente definido en el addActionCommand
		String evento = pEvento.getActionCommand( ); 
		log.info ("		>>>>>>>>>>>>Se llama al metodo:		-" + evento);
		try	{
			//Crea un objeto de la clase Method (o sea un m�todo) definido por la clase que contiene un m�todo con nombre determinado
			Method req = InterfazSuperandesApp.class.getMethod ( evento );	
			//invoca el m�todo (lo ejecuta) en la instancia de la clase que se le pasa por par�metro
			req.invoke ( this );
		} 	catch (Exception e) {//si el m�todo no existe, supongo
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por l�nea de comandos
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
