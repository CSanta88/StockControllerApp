package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.model.Articulo;
import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.repository.ProveedorRepository;
import com.example.stockcontroller.repository.ArticuloRepository;
import com.example.stockcontroller.repository.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    public List<Proveedor> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public Optional<Proveedor> obtenerProveedorMasEconomicoPorArticulo(Long articuloId) {
        return proveedorRepository.findProveedorMasBarato(articuloId);
    }

    // Método para obtener todos los artículos de un proveedor
    @Transactional
    public void asignarArticulosAProveedor(Long proveedorId, List<ProveedorArticuloDto> relaciones) {
        proveedorArticuloRepository.deleteByProveedorId(proveedorId); // Elimina relaciones previas
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        for (ProveedorArticuloDto dto : relaciones) {
            Articulo articulo = articuloRepository.findById(dto.getArticuloId())
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
            ProveedorArticulo pa = new ProveedorArticulo();
            pa.setProveedor(proveedor);
            pa.setArticulo(articulo);
            pa.setPrecioCompra(dto.getPrecioCompra());
            proveedorArticuloRepository.save(pa);
        }
    }
}
