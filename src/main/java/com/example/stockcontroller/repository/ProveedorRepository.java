package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Consulta personalizada para encontrar el proveedor más barato para un artículo
    @Query("SELECT p FROM Proveedor p WHERE p.id = (SELECT pa.proveedor.id FROM ProveedorArticulo pa WHERE pa.articulo.id = :articuloId ORDER BY pa.precioCompra ASC)")
    Optional<Proveedor> findProveedorMasBarato(@Param("articuloId") Long articuloId);

}