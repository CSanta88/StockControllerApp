package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.repository.ProveedorArticuloRepository;
import com.example.stockcontroller.service.ProveedorService;
import com.example.stockcontroller.service.ProveedorArticuloDto; // O dto. corrige si cambias de paquete
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

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @GetMapping
    public List<Proveedor> getAllProveedores() {
        return proveedorService.obtenerTodosProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        return proveedorService.obtenerProveedorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proveedor> createProveedor(@RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorService.guardarProveedor(proveedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        proveedor.setId(id);
        return ResponseEntity.ok(proveedorService.guardarProveedor(proveedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mas-barato/{articuloId}")
    public ResponseEntity<Proveedor> getProveedorMasBarato(@PathVariable Long articuloId) {
        return proveedorService.obtenerProveedorMasEconomicoPorArticulo(articuloId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // NUEVO: Asignar lista de artículos y precios a proveedor
    @PostMapping("/{id}/articulos")
    public ResponseEntity<?> asignarArticulosAProveedor(
            @PathVariable Long id,
            @RequestBody List<ProveedorArticuloDto> relaciones) {
        proveedorService.asignarArticulosAProveedor(id, relaciones);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/articulos")
    public List<ProveedorArticulo> getArticulosDeProveedor(@PathVariable Long id) {
        return proveedorArticuloRepository.findByProveedorId(id);
    }
}
