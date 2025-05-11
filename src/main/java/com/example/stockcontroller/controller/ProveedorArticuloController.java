package com.example.stockcontroller.controller;

import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.service.ProveedorArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedor-articulo")
public class ProveedorArticuloController {

    @Autowired
    private ProveedorArticuloService proveedorArticuloService;

    @GetMapping
    public List<ProveedorArticulo> obtenerTodos() {
        return proveedorArticuloService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorArticulo> obtenerPorId(@PathVariable Long id) {
        Optional<ProveedorArticulo> resultado = proveedorArticuloService.obtenerPorId(id);
        return resultado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProveedorArticulo crear(@RequestBody ProveedorArticulo proveedorArticulo) {
        return proveedorArticuloService.guardar(proveedorArticulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorArticulo> actualizar(@PathVariable Long id, @RequestBody ProveedorArticulo proveedorArticulo) {
        if (proveedorArticuloService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        proveedorArticulo.setId(id);
        return ResponseEntity.ok(proveedorArticuloService.guardar(proveedorArticulo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorArticuloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
