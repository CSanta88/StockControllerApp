package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.Usuario;
import com.example.stockcontroller.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.guardar(usuario);
        return ResponseEntity.ok(newUsuario);
    }

   // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Verificamos si el usuario existe antes de actualizarlo
        Optional<Usuario> existingUsuario = usuarioService.obtenerUsuarioPorId(id);
        if (existingUsuario.isPresent()) {
            usuario.setId(id);  // Aseguramos que el usuario a actualizar tenga el ID correcto
            Usuario updatedUsuario = usuarioService.guardar(usuario);  // Guarda el usuario actualizado
            return ResponseEntity.ok(updatedUsuario);  // Retorna el usuario actualizado
        }
        return ResponseEntity.notFound().build();  // Si el usuario no existe, retorna 404 Not Found
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        Optional<Usuario> existingUsuario = usuarioService.obtenerUsuarioPorId(id);
        if (existingUsuario.isPresent()) {
            usuarioService.eliminar(id);  // Elimina el usuario
            return ResponseEntity.noContent().build();  // Retorna código 204 No Content
        }
        return ResponseEntity.notFound().build();  // Si el usuario no existe, retorna 404 Not Found
    }
    // Autenticar un usuario
    @PostMapping("/autenticar")
    public ResponseEntity<String> autenticar(@RequestBody Usuario usuario) {
        boolean autenticado = usuarioService.autenticar(usuario.getEmail(), usuario.getContrasena());
        if (autenticado) {
            return ResponseEntity.ok("Autenticación exitosa");  // Si la autenticación es exitosa, retorna mensaje
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas");  // Si no, retorna error 401
    }

}
