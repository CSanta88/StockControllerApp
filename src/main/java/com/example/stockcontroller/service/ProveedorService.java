package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Método para obtener todos los proveedores
    public List<Proveedor> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }

    // Método para obtener un proveedor por su ID
    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    // Método para guardar o actualizar un proveedor
    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Método para eliminar un proveedor
    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    // Método para obtener el proveedor más económico para un artículo
    public Optional<Proveedor> obtenerProveedorMasEconomicoPorArticulo(Long articuloId) {
        return proveedorRepository.findProveedorMasBarato(articuloId);
    }
}
