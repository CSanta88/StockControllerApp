package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    // Métodos personalizados si es necesario
}