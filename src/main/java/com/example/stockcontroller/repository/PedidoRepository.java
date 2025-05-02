package com.example.stockcontroller.repository;

import com.example.stockcontroller.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Métodos personalizados si es necesario
}
