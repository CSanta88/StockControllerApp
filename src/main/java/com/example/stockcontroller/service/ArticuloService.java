package com.example.stockcontroller.service;

import com.example.stockcontroller.model.Articulo;
import com.example.stockcontroller.model.Pedido;
import com.example.stockcontroller.model.Proveedor;
import com.example.stockcontroller.repository.ArticuloRepository;
import com.example.stockcontroller.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Método para obtener todos los artículos
    public List<Articulo> obtenerTodosArticulos() {
        return articuloRepository.findAll();
    }

    // Método para obtener artículos cuyo stock es menor que el mínimo
    public List<Articulo> obtenerArticulosStockMinimo() {
        return articuloRepository.findByStockLessThan(10); // Ejemplo con stock mínimo 10
    }

    // Método para obtener un artículo por su ID
    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
    }

    // Método para guardar o actualizar un artículo
    public Articulo guardar(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // Método para eliminar un artículo
    public void eliminar(Long id) {
        articuloRepository.deleteById(id);
    }

    // Método para generar pedidos automáticos cuando el stock es bajo
    public void pedidoAutomaticoStockBajo() {
        List<Articulo> articulosConStockBajo = articuloRepository.findByStockLessThan(10); // Por ejemplo, stock mínimo 10

        for (Articulo articulo : articulosConStockBajo) {
            Optional<Proveedor> proveedorMasBarato = articulo.getProveedores()
                    .stream()
                    .min((p1, p2) -> p1.getPrecioArticulo(articulo).compareTo(p2.getPrecioArticulo(articulo)));

            proveedorMasBarato.ifPresent(proveedor -> {
                Pedido pedido = new Pedido();
                pedido.setArticulo(articulo);
                pedido.setProveedor(proveedor);
                pedido.setCantidad(articulo.getStockMinimo() * 2); // Suponiendo que se pide el doble del stock mínimo
                pedido.setFecha(LocalDate.now());
                pedidoRepository.save(pedido);
            });
        }
    }
}
