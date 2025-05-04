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

    // Método para autenticar un usuario (ejemplo básico de validación de contraseña)
    public boolean autenticar(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    // Método para asignar un rol a un usuario
    public void asignarRol(Usuario usuario, String rol) {
        // Este método debería actualizar el rol del usuario en la base de datos
        usuario.setRol(rol); // Suponiendo que `Usuario` tiene un campo `rol`
        usuarioRepository.save(usuario);
    }

    // En este servicio se podría implementar la Gestion para autenticacion
    // de usuarios y autorizaciones u otras operaciones relacionadas con los usuarios

}
