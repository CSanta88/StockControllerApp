package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.Articulo;
import com.example.stockcontroller.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    // Obtener todos los artículos
    @GetMapping
    public List<Articulo> getAllArticulos() {
        return articuloService.obtenerTodosArticulos();
    }

    // Obtener un artículo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Articulo> getArticuloById(@PathVariable Long id) {
        Optional<Articulo> articulo = articuloService.obtenerArticuloPorId(id);
        return articulo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Obtener artículos cuyo stock es menor que el mínimo
    @GetMapping("/stock-bajo")
    public List<Articulo> getArticulosConStockBajo(@RequestParam(defaultValue = "10") int stockMinimo) {
        return articuloService.obtenerArticulosStockMinimo(stockMinimo);
    }

    // Crear un nuevo artículo
    @PostMapping
    public ResponseEntity<Articulo> createArticulo(@RequestBody Articulo articulo) {
        Articulo newArticulo = articuloService.guardarArticulo(articulo);
        return ResponseEntity.ok(newArticulo);
    }
    // Crear un pedido automático para un artículo con stock bajo
    @PostMapping("/generar-pedidos-automaticos")
    public ResponseEntity<Void> generarPedidosAutomaticos() {
        articuloService.generarPedidosAutomaticos();
        return ResponseEntity.ok().build();
    }


    // Actualizar un artículo
    @PutMapping("/{id}")
    public ResponseEntity<Articulo> updateArticulo(@PathVariable Long id, @RequestBody Articulo articulo) {
        Articulo updatedArticulo = articuloService.guardarArticulo(articulo);
        return ResponseEntity.ok(updatedArticulo);
    }

    // Eliminar un artículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build();
    }
}
