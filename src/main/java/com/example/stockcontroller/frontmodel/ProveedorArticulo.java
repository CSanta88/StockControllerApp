package com.example.stockcontroller.frontmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

/**
 * Representa la relación entre un proveedor y un artículo,
 * incluyendo el precio ofrecido por ese proveedor para ese artículo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProveedorArticulo {
    private Long id;
    private Proveedor proveedor;
    private Articulo articulo;
    private BigDecimal precio;

    public ProveedorArticulo() {}

    /**
     * Obtiene el identificador único de la relación.
     * @return id de la relación
     */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * Obtiene el proveedor asociado a la relación.
     * @return objeto Proveedor
     */
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    /**
     * Obtiene el artículo asociado a la relación.
     * @return objeto Articulo
     */
    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    /**
     * Obtiene el precio ofertado por el proveedor para este artículo.
     * @return precio del artículo
     */
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    @Override
    public String toString() {
        return proveedor.getNombre() + " - " + articulo.getNombre() + ": " + precio + "€";
    }
}
