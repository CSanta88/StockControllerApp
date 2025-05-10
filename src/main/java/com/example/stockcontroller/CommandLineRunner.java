package com.example.stockcontroller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.stockcontroller.repository.UsuarioRepository;  // Asegúrate de que las rutas son correctas
import com.example.stockcontroller.model.Usuario;  // Asegúrate de que las rutas son correctas

@Component  // Marca la clase como un componente de Spring, para que sea ejecutada al iniciar la aplicación
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        // Lógica para inicializar la base de datos al inicio

        // Verificar cuántos usuarios hay en la base de datos
        long count = usuarioRepository.count();
        System.out.println("Número de usuarios en la base de datos: " + count);

        // Insertar un usuario de ejemplo si la base de datos está vacía
        if (count == 0) {
            Usuario usuario = new Usuario();
            usuario.setNombre("Claudio Santa");
            usuario.setEmail("claudio.santa@example.com");
            usuarioRepository.save(usuario);
            System.out.println("Usuario creado: " + usuario.getNombre());
        }
    }
}
