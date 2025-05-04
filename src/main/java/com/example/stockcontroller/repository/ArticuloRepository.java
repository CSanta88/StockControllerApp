package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    List<Articulo> findByStockLessThan(int stockMinimo);
}
