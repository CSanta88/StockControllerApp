package com.example.stockcontroller.frontmodel.DTOFront;


import com.example.stockcontroller.model.LineaPedido;

import java.math.BigDecimal;

public class LineaPedidoDTO {
    private Long id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private ArticuloDTO articulo;


    public LineaPedidoDTO(com.example.stockcontroller.model.LineaPedido linea) {
        this.id = linea.getId();
        this.cantidad = linea.getCantidad();
        this.precioUnitario = linea.getPrecioUnitario();
        this.articulo = new ArticuloDTO(linea.getArticulo());
    }
    public LineaPedidoDTO() {}

    public LineaPedidoDTO(com.example.stockcontroller.frontmodel.LineaPedido lineaPedido) {
    }


    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public ArticuloDTO getArticulo() { return articulo; }
    public void setArticulo(ArticuloDTO articulo) { this.articulo = articulo; }

    public BigDecimal getTotalLinea() {
        if (precioUnitario != null && cantidad > 0) {
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return articulo != null ? articulo.getNombre() + " x" + cantidad : "LineaPedidoDTO";
    }
}
