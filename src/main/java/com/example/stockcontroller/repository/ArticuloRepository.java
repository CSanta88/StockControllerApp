package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    // Método para encontrar artículos con stock inferior al stock mínimo
    List<Articulo> findByStockLessThan(Integer stockMinimo);

    // Método para encontrar un artículo por su nombre
    Optional<Articulo> findByNombre(String nombre);

    // Método para encontrar artículos relacionados con un proveedor
    List<Articulo> findByProveedores_Id(Long proveedorId);
}
