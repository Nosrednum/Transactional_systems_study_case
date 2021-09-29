/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

import java.math.BigDecimal;

/**
 * Clase que representa los productos contenidos dentro de en el almacenamiento
 * @author Sebastian Mujica && Anderson Barragán
 */
public class AlmacenamientoProductos implements VOAlmacenamientoProductos {

	public static final String COL_IDPRODUCTO = "IDPRODUCTO", COL_IDALMACENAMIENTO = "IDALMACENAMIENTO", COL_CANTIDAD = "CANTIDADPRODUCTOS";

	/**
	 * el identificador del producto almacenado
	 */
	public long idProducto;

	/**
	 * el identificador del almacenamiento.
	 */
	public long idAlmacenamiento;

	/**
	 * la cantidad de productos almacenados
	 */
	public int cantidadProductos;

	/**
	 * Constructor vacio.
	 */
	public AlmacenamientoProductos() {
		this.idProducto=0;
		this.idAlmacenamiento=0;
		this.cantidadProductos=0;
	}

	/**
	 * Constructor con parametros.
	 * @param idProducto
	 * @param idAlmacenamiento
	 * @param cantidadProductos
	 */
	public AlmacenamientoProductos(long idProducto, long idAlmacenamiento, int cantidadProductos) {
		this.idProducto = idProducto;
		this.idAlmacenamiento = idAlmacenamiento;
		this.cantidadProductos = cantidadProductos;
	}

	/**
	 * Constructor con parametros.
	 * @param idProducto
	 * @param idAlmacenamiento
	 * @param cantidadProductos
	 */
	public AlmacenamientoProductos(BigDecimal idProducto,BigDecimal idAlmacenamiento, BigDecimal cantidadProductos) {
		long idProductoTemp = idProducto.longValue();
		long idAlmaTemp = idAlmacenamiento.longValue();
		int cantidadTemp = cantidadProductos.intValue();
		this.idProducto = idProductoTemp;
		this.idAlmacenamiento = idAlmaTemp;
		this.cantidadProductos = cantidadTemp;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @param idAlmacenamiento the idAlmacenamiento to set
	 */
	public void setIdAlmacenamiento(long idAlmacenamiento) {
		this.idAlmacenamiento = idAlmacenamiento;
	}

	/**
	 * @param cantidadProductos the cantidadProductos to set
	 */
	public void setCantidadProductos(int cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	@Override
	public long getIdProducto() {
		return this.idProducto;
	}

	@Override
	public long getIdAlmacenamiento() {
		return this.idAlmacenamiento;
	}

	@Override
	public int cantidadProductos() {
		return this.cantidadProductos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlmacenamientoProductos [idProducto=" + idProducto + ", idAlmacenamiento=" + idAlmacenamiento
				+ ", cantidadProductos=" + cantidadProductos + "]";
	}



}
