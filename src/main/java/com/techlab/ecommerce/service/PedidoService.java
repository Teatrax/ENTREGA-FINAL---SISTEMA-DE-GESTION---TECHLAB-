package com.techlab.ecommerce.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.techlab.ecommerce.models.Pedido;
import com.techlab.ecommerce.models.LineaPedido;
import com.techlab.ecommerce.models.Producto;
import com.techlab.ecommerce.repository.PedidoRepository;
import com.techlab.ecommerce.repository.ProductoRepository;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.exception.PedidoVacioException;

@Service // Registra la clase como el "cerebro" de la lógica en Spring
public class PedidoService {

    // Inyecta los repositorios de Spring Boot para poder usarlos en la logica
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    // Constructor para inyectar los repositorios de Spring Boot
    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    // Registra el pedido entero, controla stock renglon por renglon y descuenta en
    // la BD
    public Pedido registrarPedido(Pedido pedido) throws StockInsuficienteException, PedidoVacioException {

        // Validación: Si el carrito no tiene productos, frena la ejecución
        if (pedido.getLineasPedido().isEmpty()) {
            throw new PedidoVacioException("El pedido está vacío.");
        }

        // Recorre cada renglon (linea) del pedido que mando el cliente y valida que
        // haya stock suficiente en la BD
        for (LineaPedido linea : pedido.getLineasPedido()) {
            Producto productoBD = linea.getProducto();

            // Compara las unidades deseadas contra el stock real de la BD
            if (productoBD.getStock() < linea.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para: " + productoBD.getNombre() +
                        " (Disponible: " + productoBD.getStock() + ")");
            }

            // Resta las unidades compradas al stock que teníamos
            productoBD.setStock(productoBD.getStock() - linea.getCantidad());

            // Guarda el producto con su nuevo stock modificado en PostgreSQL
            productoRepository.save(productoBD);
        }

        // Guardamos el pedido y sus renglones en las tablas "pedidos" y
        // "lineas_pedido"
        return pedidoRepository.save(pedido);
    }

    // Devuelve la lista real desde PostgreSQL para que la use el Controller
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // Spring Boot busca directo en la clave primaria de la BD
    // Devuelve un Optional por seguridad si el ID ingresado no existe
    public Optional<Pedido> buscarPedidoPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    // Un boton para poder borrar una venta de la BD si hiciera falta
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}

// 2. ¿COMO FUNCIONA EL CIRCUITO TRANSACCIONAL DE STOCK?
// - El cliente envia un objeto 'Pedido' completo (el carrito con todas sus
// lineas juntas).
// - El 'for' analiza línea por línea: extrae el producto, valida stock y resta
// el valor en memoria de Java.
// - Importante: Se debe hacer 'productoRepository.save(productoBD)' para que
// ese nuevo stock impacte en SQL.
// - Al final, 'pedidoRepository.save(pedido)' impacta la cabecera de la venta.
// Por tener 'cascade = ALL'
// en el modelo Pedido, las líneas se guardan solas en la tabla 'lineas_pedido'
// vinculadas a su 'pedido_id'.
//
// 3. ¿QUE ES EL 'Optional<Pedido>'?
// Es una caja de seguridad que introduce Java para evitar el error
// 'NullPointerException'.
// Si buscás el pedido ID 5 y existe, el Optional viene lleno con el Pedido. Si
// buscás el ID 999
// y no existe, el Optional viene vacio en lugar de romper la aplicacion con un
// valor 'null'.
