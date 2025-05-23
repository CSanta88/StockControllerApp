package com.example.stockcontroller.service;


/**
 * ProveedorArticuloDto es un Data Transfer Object (DTO) que representa la relación
 * entre un artículo y un proveedor, incluyendo el precio de compra.
 */

public class ProveedorArticuloDto {

    private Long articuloId;
    private Double precioCompra;

    public Long getArticuloId() { return articuloId; }
    public void setArticuloId(Long articuloId) { this.articuloId = articuloId; }

    public Double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(Double precioCompra) { this.precioCompra = precioCompra; }
}
