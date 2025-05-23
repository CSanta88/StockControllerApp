package com.example.stockcontroller.frontmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa un proveedor en el sistema, incluyendo sus datos de contacto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Proveedor {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    public Proveedor() {}

    /**
     * Obtiene el identificador único del proveedor.
     * @return id del proveedor
     */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * Obtiene el nombre del proveedor.
     * @return nombre del proveedor
     */
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene la dirección del proveedor.
     * @return dirección del proveedor
     */
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /**
     * Obtiene el teléfono del proveedor.
     * @return teléfono del proveedor
     */
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Obtiene el correo electrónico del proveedor.
     * @return email del proveedor
     */
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nombre;
    }
}
