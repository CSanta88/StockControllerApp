package com.example.stockcontroller.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pedidos") // Mapeo de la clase con la tabla de la base de datos
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la entidad Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

    // Relación con la entidad Articulo
    @ManyToOne
    @JoinColumn(name = "articulo_id", referencedColumnName = "id")
    private Articulo articulo;


    // Otros atributos de la clase DetallePedido
    private Integer cantidad;
    private Double precio;

    // Constructor vacío
    public DetallePedido() {
    }

    // Constructor con parámetros
    public DetallePedido(Pedido pedido, Articulo articulo, Integer cantidad, Double precio) {
        this.pedido = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
