package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Pedido;
import com.example.stockcontroller.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Método para obtener todos los pedidos
    public List<Pedido> obtenerTodosPedidos() {
        return pedidoRepository.findAll();
    }

    // Método para obtener un pedido por su ID
    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Método para guardar o actualizar un pedido
    public Pedido guardarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Método para eliminar un pedido
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
