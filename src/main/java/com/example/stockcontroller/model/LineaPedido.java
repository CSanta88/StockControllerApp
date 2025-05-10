package com.example.stockcontroller.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedidos")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public LineaPedido() {
    }

    public LineaPedido(int cantidad, BigDecimal precioUnitario, Articulo articulo, Pedido pedido) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.articulo = articulo;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    // Método para obtener el total de la línea de pedido
    public BigDecimal getTotalLinea() {
        if (precioUnitario != null && cantidad > 0) {
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }


    @Override
    public String toString() {
        return "LineaPedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", articulo=" + articulo +
                ", pedido=" + pedido +
                '}';
    }
}
