package com.example.stockcontroller.frontmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa un artículo o producto en el sistema de gestión de stock.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Articulo {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer stockMinimo;
    private List<ProveedorArticulo> proveedorArticulos;
    private List<LineaPedido> detallesPedidos;

    public Articulo() {}

    /**
     * Obtiene el identificador único del artículo.
     * @return id del artículo
     */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * Obtiene el nombre del artículo.
     * @return nombre del artículo
     */
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene la descripción del artículo.
     * @return descripción del artículo
     */
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Obtiene el precio del artículo.
     * @return precio del artículo
     */
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    /**
     * Obtiene el stock disponible del artículo.
     * @return stock del artículo
     */
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    /**
     * Obtiene el stock mínimo permitido del artículo.
     * @return stock mínimo
     */
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    /**
     * Obtiene la lista de relaciones con proveedores para este artículo.
     * @return lista de ProveedorArticulo
     */
    public List<ProveedorArticulo> getProveedorArticulos() { return proveedorArticulos; }
    public void setProveedorArticulos(List<ProveedorArticulo> proveedorArticulos) { this.proveedorArticulos = proveedorArticulos; }

    /**
     * Obtiene la lista de líneas de pedido en las que aparece este artículo.
     * @return lista de LineaPedido
     */
    public List<LineaPedido> getDetallesPedidos() { return detallesPedidos; }
    public void setDetallesPedidos(List<LineaPedido> detallesPedidos) { this.detallesPedidos = detallesPedidos; }

    @Override
    public String toString() {
        return nombre;
    }
}
