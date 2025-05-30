package com.example.stockcontroller.service;

import com.example.stockcontroller.enums.EstadoPedido;
import com.example.stockcontroller.frontmodel.DTOFront.ArticuloDTO;
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
import java.util.stream.Collectors;

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

    // === CRUD con DTO ===

    public List<ArticuloDTO> obtenerTodosDTO() {
        return articuloRepository.findAll()
                .stream()
                .map(ArticuloDTO::new)
                .collect(Collectors.toList());
    }

    public ArticuloDTO obtenerPorIdDTO(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));
        return new ArticuloDTO(articulo);
    }

    public ArticuloDTO crearArticuloDTO(ArticuloDTO dto) {
        Articulo articulo = toEntity(dto);
        Articulo guardado = articuloRepository.save(articulo);
        verificarStockYGenerarPedidoSiNecesario(guardado);
        return new ArticuloDTO(guardado);
    }

    public ArticuloDTO actualizarArticuloDTO(Long id, ArticuloDTO dto) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(BigDecimal.valueOf(dto.getPrecio()));
        articulo.setStock(dto.getStock());
        articulo.setStockMinimo(dto.getStockMinimo());

        Articulo actualizado = articuloRepository.save(articulo);
        verificarStockYGenerarPedidoSiNecesario(actualizado);

        return new ArticuloDTO(actualizado);
    }

    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }

    // === Conversión DTO <-> Entidad ===

    private Articulo toEntity(ArticuloDTO dto) {
        Articulo articulo = new Articulo();
        articulo.setId(dto.getId());
        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(BigDecimal.valueOf(dto.getPrecio()));
        articulo.setStock(dto.getStock());
        articulo.setStockMinimo(dto.getStockMinimo());
        return articulo;
    }

    // === Lógica de stock mínimo y pedido automático ===

    private void verificarStockYGenerarPedidoSiNecesario(Articulo articulo) {
        if (articulo.getStock() >= articulo.getStockMinimo()) return;

        List<ProveedorArticulo> opciones = proveedorArticuloRepository.findByArticulo(articulo);
        if (opciones.isEmpty()) return;

        ProveedorArticulo proveedorMasBarato = opciones.stream()
                .min((a, b) -> Double.compare(a.getPrecioCompra(), b.getPrecioCompra()))
                .orElse(null);

        if (proveedorMasBarato == null) return;

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDate.now());
        pedido.setProveedor(proveedorMasBarato.getProveedor());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        LineaPedido linea = new LineaPedido();
        linea.setArticulo(articulo);
        linea.setCantidad(articulo.getStockMinimo() * 2);
        linea.setPrecioUnitario(BigDecimal.valueOf(proveedorMasBarato.getPrecioCompra()));
        linea.setPedido(pedidoGuardado);

        lineaPedidoRepository.save(linea);
    }
}
