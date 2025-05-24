package com.example.stockcontroller.frontmodel.DTOFront;

import com.example.stockcontroller.frontmodel.Articulo;
import com.example.stockcontroller.frontmodel.DTOFront.ArticuloDTO;

public class ArticuloDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int stockMinimo;

    public ArticuloDTO(com.example.stockcontroller.model.Articulo articulo) {
        this.id = articulo.getId();
        this.nombre = articulo.getNombre();
        this.descripcion = articulo.getDescripcion();
        this.precio = articulo.getPrecio().doubleValue();
        this.stock = articulo.getStock();
        this.stockMinimo = articulo.getStockMinimo();
    }

    public ArticuloDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
}