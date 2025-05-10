package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.LineaPedido;
import com.example.stockcontroller.service.LineaPedidoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lineas-pedido")
public class LineaPedidoController {

    @Autowired
    private LineaPedidoService lineaPedidoService;

    // Obtener todas las líneas de pedido
    @GetMapping
    public List<LineaPedido> getAllLineasPedido() {
        return lineaPedidoService.obtenerTodasLineasPedidos();
    }

    // Obtener una línea de pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<LineaPedido> getLineaPedidoById(@PathVariable Long id) {
        try {
            LineaPedido linea = lineaPedidoService.obtenerLineaPedidoPorId(id);
            return ResponseEntity.ok(linea);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra, devuelve 404
        }
    }

    // Crear una nueva línea de pedido
    @PostMapping
    public ResponseEntity<LineaPedido> createLineaPedido(@RequestBody LineaPedido lineaPedido) {
        LineaPedido nueva = lineaPedidoService.guardarLineaPedido(lineaPedido);
        return ResponseEntity.ok(nueva);
    }

    // Actualizar una línea de pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<LineaPedido> updateLineaPedido(@PathVariable Long id, @RequestBody LineaPedido lineaPedido) {
        lineaPedido.setId(id); // Asegúrate de establecer el ID
        LineaPedido actualizada = lineaPedidoService.guardarLineaPedido(lineaPedido);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar una línea de pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLineaPedido(@PathVariable Long id) {
        lineaPedidoService.eliminarLineaPedido(id);
        return ResponseEntity.noContent().build();
    }
}
