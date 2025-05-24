package com.example.stockcontroller.frontmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

/**
 * Representa un pedido realizado a un proveedor,
 * incluyendo la fecha, estado, proveedor, detalles de las líneas y el total.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {
    private Long id;
    private LocalDate fecha;
    private String estado;
    private Proveedor proveedor;
    private BigDecimal total;
    private List<LineaPedido> detallesPedidos;

    public Pedido() {}

    /**
     * Obtiene el identificador único del pedido.
     * @return id del pedido
     */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * Obtiene la fecha del pedido.
     * @return fecha del pedido
     */
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    /**
     * Obtiene el estado actual del pedido.
     * @return estado del pedido
     */
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Obtiene el proveedor asociado a este pedido.
     * @return objeto Proveedor
     */
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    /**
     * Obtiene la lista de detalles (líneas) del pedido.
     * @return lista de LineaPedido
     */
    public List<LineaPedido> getDetallesPedidos() { return detallesPedidos; }
    public void setDetallesPedidos(List<LineaPedido> detallesPedidos) { this.detallesPedidos = detallesPedidos; }

    /**
     * Obtiene el total del pedido.
     * @return total del pedido
     */
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    @Override
    public String toString() {
        return "Pedido " + id + " (" + fecha + ")";
    }


}
