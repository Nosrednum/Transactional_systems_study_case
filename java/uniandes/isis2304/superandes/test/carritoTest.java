package uniandes.isis2304.superandes.test;

import static org.junit.Assert.fail;
import java.io.FileReader;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import main.java.uniandes.isis2304.superandes.negocio.SuperAndes;


public class carritoTest {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(carritoTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    
	/**
	 * La clase que se quiere probar
	 */
    private SuperAndes	superAndes;
	
    /* ****************************************************************
	 * 			Métodos de prueba para la tabla TipoBebida - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla TipoBebida
	 * 1. Adicionar un tipo de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un tipo de bebida por su identificador
	 * 4. Borrar un tipo de bebida por su nombre
	 */
    @Test
	public void CRDCarritoTest() 


	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre Carrito");
			superAndes = new SuperAndes(openConfig(CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Carrito  es incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Carrito es incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de SuperAndes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try{	
//    		List<long []> resp = new LinkedList<long []> ();
//    		List<Object []> tuplas =  superAndes.listarProductosDeCarrito(386);
//            for ( Object [] tupla : tuplas)
//            {
//    			long [] datosResp = new long [2];
//    		
//    			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
//    			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
//    			System.out.println( datosResp[0] + "----" + datosResp[1]);
//    			resp.add (datosResp);
//            }
			//REQUERIMIENTO 15
//    	    int cantidaProductosFacturados = superAndes.facturarCarrito(461, 22222, 1014370672);
//    	    System.out.println("facturo");
			
			//RFC 7
//			System.out.println(superAndes.fechaInicio());
			
//    		List<Long> tuplas =  superAndes.darSucursales();
			

			
			
//			superAndes.darClientesSucursal(22222);
//			superAndes.clientesFrecuentes(22222);
			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Carrito.\n";
			msg += "Revise el log de SuperAndes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Carrito");
		}
		finally
		{
			superAndes.limpiarSuperandes();
			superAndes.cerrarUnidadPersistencia ();    		
		}
	}


	/* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)


    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
