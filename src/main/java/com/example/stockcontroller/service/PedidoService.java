package com.example.stockcontroller.service;

import com.example.stockcontroller.enums.EstadoPedido;
import com.example.stockcontroller.model.Pedido;
import com.example.stockcontroller.model.LineaPedido;
import com.example.stockcontroller.model.ProveedorArticulo;
import com.example.stockcontroller.repository.PedidoRepository;
import com.example.stockcontroller.repository.LineaPedidoRepository;
import com.example.stockcontroller.repository.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

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

    // Método para crear un pedido automático para un artículo
    public Pedido crearPedidoAutomatico(Long articuloId) {
        // Buscar el proveedor más económico para ese artículo
        Optional<ProveedorArticulo> proveedorArticuloOpt = proveedorArticuloRepository
                .findTopByArticuloIdOrderByPrecioCompraAsc(articuloId);

        if (proveedorArticuloOpt.isEmpty()) {
            throw new RuntimeException("No se encontró un proveedor para el artículo ID " + articuloId);
        }

        ProveedorArticulo proveedorArticulo = proveedorArticuloOpt.get();

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setProveedor(proveedorArticulo.getProveedor());
        pedido.setFecha(LocalDate.now()); // Establecer la fecha del pedido a hoy
        pedido.setEstado(EstadoPedido.PENDIENTE);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Crear la línea de pedido asociada
        LineaPedido lineaPedido = new LineaPedido();
        lineaPedido.setPedido(pedidoGuardado);
        lineaPedido.setArticulo(proveedorArticulo.getArticulo());
        lineaPedido.setCantidad(1); // Establecer la cantidad que quieras pedir
        lineaPedido.setPrecioUnitario(BigDecimal.valueOf(proveedorArticulo.getPrecioCompra()));
        lineaPedidoRepository.save(lineaPedido);

        return pedidoGuardado;
    }
}
