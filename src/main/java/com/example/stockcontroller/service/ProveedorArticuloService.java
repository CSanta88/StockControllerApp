package com.example.stockcontroller.service;

import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.repository.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorArticuloService {

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    public List<ProveedorArticulo> obtenerTodos() {
        return proveedorArticuloRepository.findAll();
    }

    public Optional<ProveedorArticulo> obtenerPorId(Long id) {
        return proveedorArticuloRepository.findById(id);
    }

    public ProveedorArticulo guardar(ProveedorArticulo proveedorArticulo) {
        return proveedorArticuloRepository.save(proveedorArticulo);
    }

    public void eliminar(Long id) {
        proveedorArticuloRepository.deleteById(id);
    }
}
