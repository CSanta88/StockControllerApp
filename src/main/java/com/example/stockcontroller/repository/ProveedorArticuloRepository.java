package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.model.Articulo;
import com.example.stockcontroller.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorArticuloRepository extends JpaRepository<ProveedorArticulo, Long> {

    List<ProveedorArticulo> findByArticulo(Articulo articulo);

    // Método para obtener el proveedor más barato para un artículo
    @Query("SELECT pa.proveedor FROM ProveedorArticulo pa WHERE pa.articulo.id = :articuloId ORDER BY pa.precio ASC")
    Optional<Proveedor> findProveedorMasEconomicoByArticuloId(Long articuloId);

    Optional<ProveedorArticulo> findTopByArticuloIdOrderByPrecioCompraAsc(Long articuloId);

    List<ProveedorArticulo> findByProveedor(Proveedor proveedor);
}
