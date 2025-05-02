package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Articulo;
import com.example.stockcontroller.model.Pedido;
import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.repository.PedidoRepository;
import com.example.stockcontroller.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    // Método para guardar un pedido
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Método para realizar un pedido automático si el stock de un artículo es bajo
    public void realizarPedidoAutomaticoStockBajo(Articulo articulo) {
        if (articulo.getStock() < articulo.getStockMinimo()) {
            // Obtener el proveedor más económico
            Optional<Proveedor> proveedorMasBarato = articulo.getProveedores()
                    .stream()
                    .min((p1, p2) -> p1.getPrecioArticulo(articulo).compareTo(p2.getPrecioArticulo(articulo))); // Suponiendo que el método getPrecioArticulo() está definido.

            proveedorMasBarato.ifPresent(proveedor -> {
                // Crear un nuevo pedido
                Pedido pedido = new Pedido();
                pedido.setArticulo(articulo);
                pedido.setProveedor(proveedor);
                pedido.setCantidad(articulo.getStockMinimo() * 2); // Lógica para la cantidad de pedido
                pedido.setFecha(LocalDate.now());

                // Guardar el pedido
                pedidoRepository.save(pedido);
            });
        }
    }

    // Método para obtener todos los pedidos
    public Iterable<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    // Método para obtener un pedido por su ID
    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Método para eliminar un pedido
    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
