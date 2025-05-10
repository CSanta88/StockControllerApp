package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Usuario;
import com.example.stockcontroller.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para guardar un usuario
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para autenticar un usuario (dejado para el futuro)
    public boolean autenticar(String email, String password) {
        // Buscar al usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        // Si el usuario no existe, retornar falso
        if (usuario == null) {
            return false;
        }

        // Comparar la contraseña proporcionada con la almacenada
        // Si coincide la contraseña, retorna true
        if (usuario.getContrasena().equals(password)) {
            return true;
        }

        // Si la contraseña no coincide, retornar false
        return false;
    }

    // Método para asignar un rol a un usuario
    public void asignarRol(Usuario usuario, String rol) {
        // Asignar el rol al usuario
        usuario.setRol(rol);
        // Guardar los cambios en la base de datos
        usuarioRepository.save(usuario);
    }
}
