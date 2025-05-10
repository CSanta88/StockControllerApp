package com.example.stockcontroller.service;

import com.example.stockcontroller.model.LineaPedido;
import com.example.stockcontroller.repository.LineaPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineaPedidoService {

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    // Método para obtener todas las líneas de pedido
    public List<LineaPedido> obtenerTodasLineasPedidos() {
        return lineaPedidoRepository.findAll();
    }

    // Método para obtener una línea de pedido por su ID
    public LineaPedido obtenerLineaPedidoPorId(Long id) {
        return lineaPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Línea de pedido no encontrada"));
    }

    // Método para guardar o actualizar una línea de pedido
    public LineaPedido guardarLineaPedido(LineaPedido lineaPedido) {
        return lineaPedidoRepository.save(lineaPedido);
    }

    // Método para eliminar una línea de pedido
    public void eliminarLineaPedido(Long id) {
        // Verificar que la línea de pedido existe antes de eliminarla
        if (!lineaPedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar, la línea de pedido no existe");
        }
        lineaPedidoRepository.deleteById(id);
    }
}
