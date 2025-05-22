package com.example.stockcontroller.frontmodel;

import java.math.BigDecimal;

public class LineaPedido {
    private Long id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private Articulo articulo;

    public LineaPedido() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    // Puedes añadir un método para calcular el total de la línea
    public BigDecimal getTotalLinea() {
        if (precioUnitario != null && cantidad > 0) {
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return articulo != null ? articulo.getNombre() + " x" + cantidad : "LineaPedido";
    }
}
