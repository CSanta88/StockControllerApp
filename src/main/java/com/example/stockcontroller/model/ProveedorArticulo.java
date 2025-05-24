package com.example.stockcontroller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "proveedor_articulo")
public class ProveedorArticulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    private Double precioCompra;

    // Constructor por defecto (necesario para JPA)
    public ProveedorArticulo() {
    }

    // Constructor con parámetros
    public ProveedorArticulo(Articulo articulo, Proveedor proveedor, Double precioCompra) {
        this.articulo = articulo;
        this.proveedor = proveedor;
        this.precioCompra = precioCompra;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        if (precioCompra < 0) {
            throw new IllegalArgumentException("El precio de compra no puede ser negativo");
        }
        this.precioCompra = precioCompra;
    }
    @Override
    public String toString() {
        return "ProveedorArticulo{" +
                "id=" + id +
                ", articulo=" + articulo +
                ", proveedor=" + proveedor +
                ", precioCompra=" + precioCompra +
                '}';
    }
}