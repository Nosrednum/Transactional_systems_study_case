/**
 * 
 */
package main.java.uniandes.isis2304.superandes.negocio;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Sebastian Mujica
 *
 */
public class Pedido implements VOPedido{

	/**
	 * el identificador del pedido.
	 */
	private long idPedido;

	/**
	 * el identificador del producto.
	 */
	private long idProducto;

	/**
	 * la fecha de entrega del pedido.
	 */
	private Date fechaEntrega;

	/**
	 * la evaluación de la cantidad del pedido.
	 */
	private int evCant;

	/**
	 * la evaluación de la calidad del pedido.
	 */
	private int evCalidad;

	/**
	 * el precio del pedido.
	 */
	private double precioPedido;

	/**
	 * la fecha acordada para la entrega del pedido.
	 */
	private Date fechaAcordada;

	/**
	 * el proveedor que abastece el pedido.
	 */
	private long proveedor;

	/**
	 * la sucursal que realiza el pedido.
	 */
	private long sucursal;

	/**
	 * Constructor vacìo.
	 */
	public Pedido() {
		this.idPedido=0;
		this.idProducto=0;
		this.fechaEntrega= new Date(01, 01, 01);
		this.evCant = 0;
		this.evCalidad= 0;
		this.precioPedido=0;
		this.fechaAcordada= new Date(01, 01, 01);
		this.proveedor= 0;
		this.sucursal=0;
	}

	/**
	 * Constructor usando paràmetros.
	 * @param idPedido
	 * @param idProducto
	 * @param fechaEntrega
	 * @param evCant
	 * @param evCalidad
	 * @param precioPedido
	 * @param fechaAcordada
	 * @param proveedor
	 * @param sucursal
	 */
	public Pedido(long idPedido, long idProducto, Date fechaEntrega, int evCant, int evCalidad,
			double precioPedido, Date fechaAcordada, long proveedor, long sucursal) {
		this.idPedido = idPedido;
		this.fechaEntrega = fechaEntrega;
		this.evCant = evCant;
		this.evCalidad = evCalidad;
		this.precioPedido = precioPedido;
		this.fechaAcordada = fechaAcordada;
		this.proveedor = proveedor;
		this.sucursal = sucursal;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @param fechaEntrega the fechaEntrega to set
	 */
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * @param evCant the evCant to set
	 */
	public void setEvCant(int evCant) {
		this.evCant = evCant;
	}

	/**
	 * @param evCalidad the evCalidad to set
	 */
	public void setEvCalidad(int evCalidad) {
		this.evCalidad = evCalidad;
	}

	/**
	 * @param precioPedido the precioPedido to set
	 */
	public void setPrecioPedido(double precioPedido) {
		this.precioPedido = precioPedido;
	}

	/**
	 * @param fechaAcordada the fechaAcordada to set
	 */
	public void setFechaAcordada(Date fechaAcordada) {
		this.fechaAcordada = fechaAcordada;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(long proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public long getIdPedido() {
		// TODO Auto-generated method stub
		return this.idPedido;
	}

	@Override
	public long getIdProducto() {
		// TODO Auto-generated method stub
		return this.idProducto;
	}

	@Override
	public Date getFechaEntrega() {
		// TODO Auto-generated method stub
		return this.fechaEntrega;
	}

	@Override
	public int getEvCant() {
		// TODO Auto-generated method stub
		return this.evCant;
	}

	@Override
	public int getEvCalidad() {
		// TODO Auto-generated method stub
		return this.evCalidad;
	}

	@Override
	public double getPrecioPedido() {
		// TODO Auto-generated method stub
		return this.precioPedido;
	}

	@Override
	public Date getFechaAcordada() {
		// TODO Auto-generated method stub
		return this.fechaAcordada;
	}

	@Override
	public long getProveedor() {
		// TODO Auto-generated method stub
		return this.proveedor;
	}

	@Override
	public long getSucursal() {
		// TODO Auto-generated method stub
		return this.sucursal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", idProducto=" + idProducto + ", fechaEntrega=" + fechaEntrega
				+ ", evCant=" + evCant + ", evCalidad=" + evCalidad + ", precioPedido=" + precioPedido
				+ ", fechaAcordada=" + fechaAcordada + ", proveedor=" + proveedor + ", sucursal=" + sucursal + "]";
	}


}
