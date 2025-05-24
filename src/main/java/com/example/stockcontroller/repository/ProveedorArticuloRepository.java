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

    // Métodos para buscar relaciones por proveedor o artículo
    List<ProveedorArticulo> findByArticulo(Articulo articulo);
    List<ProveedorArticulo> findByProveedorId(Long proveedorId);
    List<ProveedorArticulo> findByArticuloId(Long articuloId);
    List<ProveedorArticulo> findByArticuloIdAndProveedorId(Long articuloId, Long proveedorId);

    // Método para eliminar todas las relaciones de un proveedor
    void deleteByProveedorId(Long proveedorId);

    // Método para obtener el proveedor más barato para un artículo
    @Query("SELECT pa.proveedor FROM ProveedorArticulo pa WHERE pa.articulo.id = :articuloId ORDER BY pa.precioCompra ASC")
    Optional<Proveedor> findProveedorMasEconomicoByArticuloId(Long articuloId);

    Optional<ProveedorArticulo> findTopByArticuloIdOrderByPrecioCompraAsc(Long articuloId);


    @Query("SELECT pa FROM ProveedorArticulo pa " +
            "JOIN FETCH pa.articulo " +
            "JOIN FETCH pa.proveedor " +
            "WHERE pa.proveedor.id = :proveedorId")
    List<ProveedorArticulo> findByProveedorIdConJoinFetch(Long proveedorId);

}
