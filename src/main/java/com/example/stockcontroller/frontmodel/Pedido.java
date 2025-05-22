package com.example.stockcontroller.frontmodel;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private Long id;
    private LocalDate fecha;
    private String estado;    // Puedes cambiarlo a Enum si lo necesitas
    private Proveedor proveedor;
    private java.math.BigDecimal total; // Puedes calcularlo si quieres mostrar el total
    private java.util.List<LineaPedido> detallesPedidos;

    public Pedido() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public java.util.List<LineaPedido> getDetallesPedidos() { return detallesPedidos; }
    public void setDetallesPedidos(List<LineaPedido> detallesPedidos) { this.detallesPedidos = detallesPedidos; }

    public java.math.BigDecimal getTotal() { return total; }
    public void setTotal(java.math.BigDecimal total) { this.total = total; }

    @Override
    public String toString() {
        return "Pedido " + id + " (" + fecha + ")";
    }
}
