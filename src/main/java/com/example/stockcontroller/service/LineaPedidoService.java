package com.example.stockcontroller.service;

import com.example.stockcontroller.model.LineaPedido;
import com.example.stockcontroller.repository.LineaPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineaPedidoService {

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    // Método para obtener todas las líneas de pedido
    public List<LineaPedido> obtenerTodasLineasPedidos() {
        return lineaPedidoRepository.findAll();
    }

    // Método para obtener una línea de pedido por su ID
    public Optional<LineaPedido> obtenerLineaPedidoPorId(Long id) {
        return lineaPedidoRepository.findById(id);
    }

    // Método para guardar o actualizar una línea de pedido
    public LineaPedido guardarLineaPedido(LineaPedido lineaPedido) {
        return lineaPedidoRepository.save(lineaPedido);
    }

    // Método para eliminar una línea de pedido
    public void eliminarLineaPedido(Long id) {
        lineaPedidoRepository.deleteById(id);
    }
}
