package com.example.stockcontroller.service;


import com.example.stockcontroller.enums.EstadoPedido;
import com.example.stockcontroller.model.*;
import com.example.stockcontroller.repository.ArticuloRepository;
import com.example.stockcontroller.repository.LineaPedidoRepository;
import com.example.stockcontroller.repository.PedidoRepository;
import com.example.stockcontroller.repository.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    // Método para obtener todos los artículos
    public List<Articulo> obtenerTodosArticulos() {
        return articuloRepository.findAll();
    }

    // Método para obtener artículos cuyo stock es menor que el mínimo
    public List<Articulo> obtenerArticulosStockMinimo(int stockMinimo) {
        return articuloRepository.findByStockLessThan(stockMinimo);  // Método ya definido en el repositorio
    }

    // Método para obtener un artículo por su ID
    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
    }

    // Método para guardar o actualizar un artículo
    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // Método para eliminar un artículo
    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }

    // Método para generar pedidos automáticos cuando el stock es bajo
    public void generarPedidosAutomaticos() {
        List<Articulo> articulosConStockBajo = obtenerArticulosStockMinimo(10); // Asumiendo que pasas el stock mínimo

        for (Articulo articulo : articulosConStockBajo) {
            Optional<Proveedor> proveedorMasBarato = proveedorArticuloRepository
                    .findProveedorMasEconomicoByArticuloId(articulo.getId());

            proveedorMasBarato.ifPresent(proveedor -> {

                // Crear pedido
                Pedido pedido = new Pedido();
                pedido.setFecha(LocalDate.now());
                pedido.setProveedor(proveedor);
                pedido.setEstado(EstadoPedido.PENDIENTE);

                Pedido pedidoGuardado = pedidoRepository.save(pedido);

                // Crear línea de pedido
                LineaPedido lineaPedido = new LineaPedido();
                lineaPedido.setArticulo(articulo);
                lineaPedido.setCantidad(articulo.getStockMinimo() * 2); // Suponiendo que se pide el doble del stock mínimo


                //lineaPedido.setPrecioUnitario(proveedorArticulo.getPrecio());
                ProveedorArticulo proveedorArticulo = proveedorArticuloRepository.findByArticulo(articulo).get(0);
                BigDecimal precioUnitario = BigDecimal.valueOf(proveedorArticulo.getPrecioCompra());  // Convertir a BigDecimal
                lineaPedido.setPedido(pedidoGuardado);

                lineaPedidoRepository.save(lineaPedido);
            });
        }
    }
}
