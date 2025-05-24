package com.example.stockcontroller.frontmodel.DTOFront;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// Puedes añadir @JsonIgnoreProperties si lo necesitas para Jackson
public class PedidoDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;
    private ProveedorDTO proveedor;
    private BigDecimal total;
    private List<LineaPedidoDTO> detallesPedidos;


    public PedidoDTO() {}


    public PedidoDTO(com.example.stockcontroller.frontmodel.Pedido pedido) {
        this.id = pedido.getId();
        this.fecha = pedido.getFecha();
        this.estado = pedido.getEstado();
        this.proveedor = pedido.getProveedor() != null ? new ProveedorDTO(pedido.getProveedor()) : null;
        this.total = pedido.getTotal();
        if (pedido.getDetallesPedidos() != null) {
            this.detallesPedidos = pedido.getDetallesPedidos()
                    .stream()
                    .map(LineaPedidoDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Constructor desde model.Pedido (si tienes una clase de modelo diferente en backend)
    public PedidoDTO(com.example.stockcontroller.model.Pedido pedido) {
        this.id = pedido.getId();
        this.fecha = pedido.getFecha();
        this.estado = pedido.getEstado() != null ? pedido.getEstado().toString() : null;
        this.proveedor = pedido.getProveedor() != null ? new ProveedorDTO(pedido.getProveedor()) : null;
        this.total = pedido.getTotalPedido();
        if (pedido.getDetallesPedidos() != null) {
            this.detallesPedidos = pedido.getDetallesPedidos()
                    .stream()
                    .map(LineaPedidoDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ProveedorDTO getProveedor() { return proveedor; }
    public void setProveedor(ProveedorDTO proveedor) { this.proveedor = proveedor; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<LineaPedidoDTO> getDetallesPedidos() { return detallesPedidos; }
    public void setDetallesPedidos(List<LineaPedidoDTO> detallesPedidos) { this.detallesPedidos = detallesPedidos; }

    @Override
    public String toString() {
        return "Pedido " + id + " (" + fecha + ")";
    }
}
