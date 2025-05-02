package com.example.stockcontroller.service;

import com.example.stockcontroller.model.DetallePedido;
import com.example.stockcontroller.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    // Método para guardar un detalle de pedido
    public DetallePedido guardar(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    // Otros métodos para gestionar detalles de pedidos
}
