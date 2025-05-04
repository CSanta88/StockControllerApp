package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.LineaPedido;
import com.example.stockcontroller.service.LineaPedidoService;
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
        return lineaPedidoService.findAll();
    }

    // Obtener una línea de pedido por su ID
    @GetMapping("/{id}")
    public ResponseEntity<LineaPedido> getLineaPedidoById(@PathVariable Long id) {
        Optional<LineaPedido> lineaPedido = lineaPedidoService.findById(id);
        return lineaPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva línea de pedido
    @PostMapping
    public ResponseEntity<LineaPedido> createLineaPedido(@RequestBody LineaPedido lineaPedido) {
        LineaPedido newLineaPedido = lineaPedidoService.save(lineaPedido);
        return ResponseEntity.ok(newLineaPedido);
    }

    // Actualizar una línea de pedido
    @PutMapping("/{id}")
    public ResponseEntity<LineaPedido> updateLineaPedido(@PathVariable Long id, @RequestBody LineaPedido lineaPedido) {
        LineaPedido updatedLineaPedido = lineaPedidoService.update(id, lineaPedido);
        return ResponseEntity.ok(updatedLineaPedido);
    }

    // Eliminar una línea de pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLineaPedido(@PathVariable Long id) {
        lineaPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
