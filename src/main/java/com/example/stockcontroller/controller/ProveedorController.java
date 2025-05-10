package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Obtener todos los proveedores
    @GetMapping
    public List<Proveedor> getAllProveedores() {
        return proveedorService.obtenerTodosProveedores();
    }

    // Obtener proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        return proveedorService.obtenerProveedorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo proveedor
    @PostMapping
    public ResponseEntity<Proveedor> createProveedor(@RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorService.guardarProveedor(proveedor));
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        proveedor.setId(id);
        return ResponseEntity.ok(proveedorService.guardarProveedor(proveedor));
    }

    // Eliminar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener proveedor más económico para un artículo
    @GetMapping("/mas-barato/{articuloId}")
    public ResponseEntity<Proveedor> getProveedorMasBarato(@PathVariable Long articuloId) {
        return proveedorService.obtenerProveedorMasEconomicoPorArticulo(articuloId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
