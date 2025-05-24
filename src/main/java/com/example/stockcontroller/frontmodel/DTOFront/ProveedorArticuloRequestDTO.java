package com.example.stockcontroller.frontmodel.DTOFront;

public class ProveedorArticuloRequestDTO {

    private Long articuloId;
    private Double precioCompra;

    public ProveedorArticuloRequestDTO() {}

    public ProveedorArticuloRequestDTO(Long articuloId, Double precioCompra) {
        this.articuloId = articuloId;
        this.precioCompra = precioCompra;
    }

    public Long getArticuloId() { return articuloId; }
    public void setArticuloId(Long articuloId) { this.articuloId = articuloId; }

    public Double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(Double precioCompra) { this.precioCompra = precioCompra; }
}
