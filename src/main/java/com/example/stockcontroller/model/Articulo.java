package com.example.stockcontroller.model;

import com.jayway.jsonpath.internal.function.numeric.Min;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "articulos")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @Column(precision = 10, scale = 2)//Para manejar correctamente el precio con decimales
    private BigDecimal precio;

    private int stock;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProveedorArticulo> proveedorArticulos;

    @OneToMany(mappedBy = "articulo")
    private List<LineaPedido> detallesPedidos;

    public Articulo() {
    }

    public Articulo(String nombre, String descripcion, BigDecimal precio, int stock, int stockMinimo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public List<ProveedorArticulo> getProveedorArticulos() {
        return proveedorArticulos;
    }

    public void setProveedorArticulos(List<ProveedorArticulo> proveedorArticulos) {
        this.proveedorArticulos = proveedorArticulos;
    }

    public List<LineaPedido> getDetallesPedidos() {
        return detallesPedidos;
    }

    public void setDetallesPedidos(List<LineaPedido> detallesPedidos) {
        this.detallesPedidos = detallesPedidos;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                '}';
    }
}
