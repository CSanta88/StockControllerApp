package com.example.stockcontroller.model;

import com.example.stockcontroller.enums.EstadoPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> detallesPedidos;

    public Pedido() {
    }

    public Pedido(LocalDate fecha, EstadoPedido estado, Proveedor proveedor) {
        this.fecha = fecha;
        this.estado = estado;
        this.proveedor = proveedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<LineaPedido> getDetallesPedidos() {
        return detallesPedidos;
    }

    public void setDetallesPedidos(List<LineaPedido> detallesPedidos) {
        this.detallesPedidos = detallesPedidos;
    }


    public BigDecimal getTotalPedido() {
        return detallesPedidos.stream()
                .map(LineaPedido::getTotalLinea)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                ", proveedor=" + proveedor +
                '}';
    }

    public boolean isPendiente() {
        return estado == EstadoPedido.PENDIENTE;
    }
}
