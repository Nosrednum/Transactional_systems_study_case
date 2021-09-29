package main.java.uniandes.isis2304.superandes.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements VOCarrito{

	public static final String COL_IDCARRITO = "ID_CARRITO", COL_DECLIENTE = "DECLIENTE", COL_ESTADO = "ESTADO";
	public static final String DISPONIBLE = "DISPONIBLE",OCUPADO = "OCUPADO", ABANDONADO = "ABANDONADO";

	public long id_carrito;
	public long decliente;
	public String estado;
	private List<Object> productosEnCarrito;

	public Carrito() {
		this(0,DISPONIBLE,0);
	}

	public Carrito(BigDecimal idCarrito, String estado, BigDecimal deCliente) {
		long idCa = idCarrito.longValue();
		long idCl = deCliente.longValue();
		this.id_carrito = idCa;
		this.decliente = idCl;
		this.estado = estado;
		this.productosEnCarrito = new ArrayList<>();
	}

	public Carrito(long idCarrito, String estado, long idCliente) {
		this.id_carrito = idCarrito;
		this.decliente = idCliente;
		this.estado = estado;
		this.productosEnCarrito = new ArrayList<>();
	}

	/* ************
	 * guetosisetos :v
	 **************/
	public long getId_carrito() {return this.id_carrito;}
	public long getDecliente() {return this.decliente;}
	public String getEstado() {return this.estado;}
	public List<Object> getProductosEnCarrito(){return this.productosEnCarrito;}

	public void setId_carrito(long idCarrito) {
		this.id_carrito = idCarrito;
	}

	public void setProductosCarrito(List<Object> lista){
		this.productosEnCarrito = lista;
	}

	public void setDecliente(long idCliente) {
		this.decliente = idCliente;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override public String toString() {
		String toRet = "Carrito [Carrito id = " + id_carrito + ", cliente =" + decliente
				+ ", estado =" + estado + " productos: {";
		for (Object ap : productosEnCarrito) 
			toRet = toRet + ap +", ";
		return  toRet + " }]";
	}

	public String toStringCompleto() {
		// TODO Auto-generated method stub
		return null;
	}


}
