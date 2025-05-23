package com.example.stockcontroller.frontmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa un usuario del sistema con sus credenciales y rol asignado.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    private String rol;

    public Usuario() {}

    /**
     * Obtiene el identificador único del usuario.
     * @return id del usuario
     */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * Obtiene el nombre del usuario.
     * @return nombre del usuario
     */
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return email del usuario
     */
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Obtiene la contraseña (hash) del usuario.
     * @return contraseña del usuario
     */
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    /**
     * Obtiene el rol asignado al usuario.
     * @return rol del usuario
     */
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return nombre;
    }
}
