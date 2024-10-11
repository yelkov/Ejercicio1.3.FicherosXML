package xmlEj.pedidos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idPedido;
    private String nombreCliente;
    private List<Producto> productos = new ArrayList<Producto>();

    public Pedido() {
    }
    public Pedido(Integer idPedido, String nombreCliente) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
    }

    public Pedido(Integer idPedido, String nombreCliente, List<Producto> productos) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.productos = productos;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Pedido addProducto(Producto producto) {
        this.productos.add(producto);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdPedido: " + getIdPedido());
        sb.append("\nNombreCliente: " + getNombreCliente());
        sb.append("\nProductos: " + getProductos().toString());
        return sb.toString();
    }
}
