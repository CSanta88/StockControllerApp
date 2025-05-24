package com.example.stockcontroller.controller;


import com.example.stockcontroller.frontmodel.DTOFront.ArticuloDTO;
import com.example.stockcontroller.frontmodel.DTOFront.ProveedorArticuloResponseDTO;
import com.example.stockcontroller.frontmodel.DTOFront.ProveedorDTO;
import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.repository.ProveedorArticuloRepository;
import com.example.stockcontroller.service.ProveedorService;
import com.example.stockcontroller.service.ProveedorArticuloDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/{id}/articulos")
    public List<ProveedorArticuloResponseDTO> getArticulosDeProveedor(@PathVariable Long id) {
        List<ProveedorArticulo> relaciones = proveedorArticuloRepository.findByProveedorIdConJoinFetch(id);
        return relaciones.stream()
                .map(pa -> {
                    ProveedorArticuloResponseDTO dto = new ProveedorArticuloResponseDTO();
                    dto.setId(pa.getId());
                    dto.setProveedor(new ProveedorDTO(pa.getProveedor()));
                    dto.setArticulo(new ArticuloDTO(pa.getArticulo()));
                    dto.setPrecioCompra(pa.getPrecioCompra());
                    return dto;
                })
                .toList();
    }
    // Endpoint para asignar artículos a un proveedor (por su ID)
    @PostMapping("/{proveedorId}/articulos")
    public ResponseEntity<?> asignarArticulosAProveedor(
            @PathVariable Long proveedorId,
            @RequestBody List<ProveedorArticuloDto> relaciones
    ) {
        proveedorService.asignarArticulosAProveedor(proveedorId, relaciones);
        return ResponseEntity.ok().build();
    }

}
