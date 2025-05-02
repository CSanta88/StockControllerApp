package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT p FROM Proveedor p WHERE p.id = (SELECT a.proveedor.id FROM Articulo a WHERE a.id = ?1 ORDER BY a.precio ASC LIMIT 1)")
    Proveedor findProveedorMasBarato(Long articuloId);
}
