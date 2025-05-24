package com.example.stockcontroller.frontmodel.DTOFront;


import com.example.stockcontroller.frontmodel.Proveedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProveedorDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;


    public ProveedorDTO(com.example.stockcontroller.model.Proveedor proveedor) {
        this.id = proveedor.getId();
        this.nombre = proveedor.getNombre();
        this.direccion = proveedor.getDireccion();
        this.telefono = proveedor.getTelefono();
        this.email = proveedor.getEmail();
    }

    public ProveedorDTO(Proveedor proveedor) {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}