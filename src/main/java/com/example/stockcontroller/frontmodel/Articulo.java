package com.example.stockcontroller.frontmodel;

import java.math.BigDecimal;

public class Articulo {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private int stockMinimo;

    public Articulo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    @Override
    public String toString() {
        return nombre;
    }
}
