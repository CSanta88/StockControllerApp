package com.example.stockcontroller.controller;

import com.example.stockcontroller.frontmodel.DTOFront.ArticuloDTO;
import com.example.stockcontroller.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    // Obtener todos los artículos (DTO)
    @GetMapping
    public List<ArticuloDTO> getAllArticulos() {
        return articuloService.obtenerTodosDTO();
    }

    // Obtener un artículo por su ID (DTO)
    @GetMapping("/{id}")
    public ResponseEntity<ArticuloDTO> getArticuloById(@PathVariable Long id) {
        try {
            ArticuloDTO articuloDTO = articuloService.obtenerPorIdDTO(id);
            return ResponseEntity.ok(articuloDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo artículo (DTO)
    @PostMapping
    public ResponseEntity<ArticuloDTO> createArticulo(@RequestBody ArticuloDTO articuloDTO) {
        ArticuloDTO creado = articuloService.crearArticuloDTO(articuloDTO);
        return ResponseEntity.ok(creado);
    }

    // Actualizar un artículo existente (DTO)
    @PutMapping("/{id}")
    public ResponseEntity<ArticuloDTO> updateArticulo(@PathVariable Long id, @RequestBody ArticuloDTO articuloDTO) {
        try {
            ArticuloDTO actualizado = articuloService.actualizarArticuloDTO(id, articuloDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un artículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build();
    }
}
