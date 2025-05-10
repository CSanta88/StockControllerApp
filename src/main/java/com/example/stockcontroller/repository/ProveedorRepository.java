package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Método para encontrar el proveedor más barato para un artículo
    Optional<Proveedor> findProveedorMasBarato(Long articuloId);

}