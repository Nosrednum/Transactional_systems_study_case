package uniandes.isis2304.superandes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotEquals;



import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import main.java.uniandes.isis2304.superandes.negocio.Carrito;
import main.java.uniandes.isis2304.superandes.negocio.SuperAndes;

public class RFC7Test {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(carritoTest.class.getName());

	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD tambi�n
	 */
	private static final String CONFIG_TABLAS_A = "./resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	/**
	 * La clase que se quiere probar
	 */
	private SuperAndes	superAndes;



	@Test
	public void RFCTest() 

	{
		// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre SuperAndes");
			superAndes = new SuperAndes(openConfig(CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
			//			e.printStackTrace();
			log.info ("Prueba de CRD de conexi�n es incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de conexi�n de Carrito es incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de SuperAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		// Ahora si se pueden probar las operaciones
		try{	
			
			/**
			 *REQUERIMIETNO FUNCIONA DE CONSULTA 7
			 *Dada una unidad de tiempo (por ejemplo, semana o mes) y un tipo de producto, considerando todo el tiempo
			 *de operaci�n de SuperAndes, indicar cu�les fueron las fechas de mayor demanda (mayor cantidad de
			 *productos), las de mayores ingresos (mayor cantidad de dinero recibido) y las de menor demanda. Se deben
			 *presentar los resultados de manera individual para cada sucursal.
			 */
			
			//Se deber�an esperar 0 l�neas de an�lisis, ya que no existe un producto con el id dado.
			int resultado = superAndes.analizarOperacionSuperAndes(18, 10).size();
			assertEquals(0, resultado);
			
			//Se espera que los resultados sean diferentes ya que existen l�neas de an�lisis para el producto dado.
			List<String[]> rta = superAndes.analizarOperacionSuperAndes(1, 10);
			int resultado2 = rta.size();
			assertNotEquals(0, resultado2);
			for (String[] strings : rta) {
				System.out.println("Sucursal = " + strings[0] + "---" + strings[1]+ "---" +strings[2]+ "---" + strings[3]);
			}
			
			
		}
		catch (Exception e)
		{
			//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre los requerimientos funcionales de consulta.\n";
			msg += "Revise el log de SuperAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

			fail ("Error en las pruebas sobre los RQF de CONSULTA");
		}
		finally
		{
			superAndes.limpiarSuperandes();
			superAndes.cerrarUnidadPersistencia ();    		
		}
	}


	/* ****************************************************************
	 * 			M�todos de configuraci�n
	 *****************************************************************/
	/**
	 * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuraci�n deseada
	 * @param archConfig - Archivo Json que contiene la configuraci�n
	 * @return Un objeto JSON con la configuraci�n del tipo especificado
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
			log.info ("Se encontr� un archivo de configuraci�n de tablas v�lido");
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de tablas v�lido: ", "RFCTest", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}	
}

