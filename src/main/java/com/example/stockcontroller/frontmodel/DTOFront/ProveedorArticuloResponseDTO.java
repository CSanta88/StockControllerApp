package com.example.stockcontroller.frontmodel.DTOFront;

public class ProveedorArticuloResponseDTO {
    private Long id;
    private ProveedorDTO proveedor;
    private ArticuloDTO articulo;
    private Double precioCompra;

    public ProveedorArticuloResponseDTO() {}
    public ProveedorArticuloResponseDTO(Long id, ProveedorDTO proveedor, ArticuloDTO articulo, Double precioCompra) {
        this.id = id;
        this.proveedor = proveedor;
        this.articulo = articulo;
        this.precioCompra = precioCompra;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProveedorDTO getProveedor() { return proveedor; }
    public void setProveedor(ProveedorDTO proveedor) { this.proveedor = proveedor; }

    public ArticuloDTO getArticulo() { return articulo; }
    public void setArticulo(ArticuloDTO articulo) { this.articulo = articulo; }

    public Double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(Double precioCompra) { this.precioCompra = precioCompra; }
}
