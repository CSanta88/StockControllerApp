package com.example.stockcontroller.frontmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

/**
 * Representa una línea individual dentro de un pedido,
 * incluyendo la cantidad, el artículo y el precio unitario.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineaPedido {
    private Long id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private Articulo articulo;

    public LineaPedido() {}

    /**
     * Obtiene el identificador único de la línea de pedido.
     * @return id de la línea de pedido
     */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * Obtiene la cantidad de artículos en esta línea de pedido.
     * @return cantidad de artículos
     */
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    /**
     * Obtiene el precio unitario del artículo en esta línea.
     * @return precio unitario
     */
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    /**
     * Obtiene el artículo correspondiente a esta línea de pedido.
     * @return objeto Articulo
     */
    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    /**
     * Calcula el total de esta línea de pedido (precio unitario * cantidad).
     * @return total de la línea de pedido
     */
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
