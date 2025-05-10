package com.example.stockcontroller.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;
    private String email;
    private String contrasena;
    private String rol;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String contrasena, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Expresión regular para validar el correo electrónico
        String userEmail = "^[A-Za-z0-9._%+-]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Verificar si el email es válido
        if (email == null || !email.matches(userEmail)) {
            throw new IllegalArgumentException("El email debe tener un formato válido.");
        }

        // Si pasa la validación, asignamos el valor
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        String contrasenaValida = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";

        //Verificar si la contraseña cumple con el formato
        if(contrasena==null || !contrasena.matches(contrasenaValida)){
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, incluyendo letras, números y caracteres especiales.");
        }
        // Si pasa la validación, asignamos el valor
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
