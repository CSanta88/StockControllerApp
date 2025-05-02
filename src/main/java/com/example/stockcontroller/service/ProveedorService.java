package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Método para obtener el proveedor más barato de un artículo
    public Proveedor obtenerProveedorMasBarato(Long articuloId) {
        return proveedorRepository.findProveedorMasBarato(articuloId);
    }

    // Otros métodos para gestión de proveedores
}
