package com.example.servicio_pedidos.service;

import com.example.servicio_pedidos.client.InventarioClient;
import com.example.servicio_pedidos.client.LibroClient;
import com.example.servicio_pedidos.dto.InventarioDTO;
import com.example.servicio_pedidos.dto.LibroDTO;
import com.example.servicio_pedidos.model.DetallePedido;
import com.example.servicio_pedidos.model.Pedido;
import com.example.servicio_pedidos.repository.DetallePedidoRepository;
import com.example.servicio_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final LibroClient libroClient;
    private final InventarioClient inventarioClient;

    public PedidoService(PedidoRepository pedidoRepository,
                         DetallePedidoRepository detallePedidoRepository,
                         LibroClient libroClient,
                         InventarioClient inventarioClient) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.libroClient = libroClient;
        this.inventarioClient = inventarioClient;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> buscarPorClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> buscarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<DetallePedido> buscarDetallesPorLibroId(Long libroId) {
        return detallePedidoRepository.findByLibroId(libroId);
    }

    public Pedido guardarPedido(Pedido pedido) {
        logger.info("Iniciando creación de pedido para cliente ID: {}", pedido.getClienteId());

        if (pedido.getFechaPedido() == null) {
            pedido.setFechaPedido(LocalDate.now());
        }

        if (pedido.getEstado() == null) {
            pedido.setEstado("PENDIENTE");
        }

        double total = 0;

        for (DetallePedido detalle : pedido.getDetalles()) {
            logger.info("Validando libro ID: {}", detalle.getLibroId());

            LibroDTO libro = libroClient.obtenerLibroPorId(detalle.getLibroId());

            if (libro == null) {
                logger.warn("No se encontró el libro ID: {}", detalle.getLibroId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El libro no existe");
            }

            InventarioDTO inventario = inventarioClient.obtenerInventarioPorLibroId(detalle.getLibroId());

            if (inventario == null) {
                logger.warn("No se encontró inventario para el libro ID: {}", detalle.getLibroId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El libro no tiene inventario registrado");
            }

            if (inventario.getStockActual() < detalle.getCantidad()) {
                logger.warn("Stock insuficiente para libro ID: {}", detalle.getLibroId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay stock suficiente");
            }

            double subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();
            detalle.setSubtotal(subtotal);
            detalle.setPedido(pedido);
            total += subtotal;
        }

        pedido.setTotal(total);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        logger.info("Pedido creado correctamente con ID: {}", pedidoGuardado.getId());

        return pedidoGuardado;
    }

    public Pedido actualizarPedido(Long id, Pedido pedido) {
        logger.info("Iniciando actualización de pedido ID: {}", id);

        Pedido pedidoExistente = buscarPorId(id);

        if (pedidoExistente == null) {
            logger.warn("No se encontró el pedido ID: {}", id);
            return null;
        }

        pedidoExistente.setClienteId(pedido.getClienteId());
        pedidoExistente.setFechaPedido(pedido.getFechaPedido());
        pedidoExistente.setEstado(pedido.getEstado());

        pedidoExistente.getDetalles().clear();

        double total = 0;

        for (DetallePedido detalle : pedido.getDetalles()) {
            logger.info("Validando libro ID: {}", detalle.getLibroId());

            LibroDTO libro = libroClient.obtenerLibroPorId(detalle.getLibroId());

            if (libro == null) {
                logger.warn("No se encontró el libro ID: {}", detalle.getLibroId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El libro no existe");
            }

            InventarioDTO inventario = inventarioClient.obtenerInventarioPorLibroId(detalle.getLibroId());

            if (inventario == null) {
                logger.warn("No se encontró inventario para el libro ID: {}", detalle.getLibroId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El libro no tiene inventario registrado");
            }

            if (inventario.getStockActual() < detalle.getCantidad()) {
                logger.warn("Stock insuficiente para libro ID: {}", detalle.getLibroId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay stock suficiente");
            }

            double subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();
            detalle.setSubtotal(subtotal);
            detalle.setPedido(pedidoExistente);
            pedidoExistente.getDetalles().add(detalle);
            total += subtotal;
        }

        pedidoExistente.setTotal(total);

        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);

        logger.info("Pedido actualizado correctamente con ID: {}", pedidoActualizado.getId());

        return pedidoActualizado;
    }

    public boolean eliminarPedido(Long id) {
        Pedido pedido = buscarPorId(id);

        if (pedido == null) {
            return false;
        }

        pedidoRepository.delete(pedido);
        return true;
    }

    public long contarPedidos() {
        return pedidoRepository.count();
    }
}