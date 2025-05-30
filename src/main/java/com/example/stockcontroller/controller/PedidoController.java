package com.example.stockcontroller.controller;

import com.example.stockcontroller.enums.EstadoPedido;
import com.example.stockcontroller.model.Pedido;
import com.example.stockcontroller.service.PedidoService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.obtenerTodosPedidos();
    }

    // Obtener un pedido por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.obtenerPedidoPorId(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    // Actualizar un pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        pedido.setId(id); // Asegúrate de establecer el ID
        Pedido pedidoActualizado = pedidoService.guardarPedido(pedido);
        return ResponseEntity.ok(pedidoActualizado);
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // Crear un pedido automático para un artículo con stock bajo
    @PostMapping("/auto")
    public ResponseEntity<Pedido> crearPedidoAutomatico(@RequestParam Long articuloId) {
        try {
            Pedido pedido = pedidoService.crearPedidoAutomatico(articuloId);
            return ResponseEntity.ok(pedido);
       } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
