package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlab.ecommerce.exception.PedidoVacioException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.models.Pedido;
import com.techlab.ecommerce.service.PedidoService;

@RestController // Avisa a Spring que esta clase maneja peticiones HTTP y responde en JSON
@CrossOrigin(origins = "*")
@RequestMapping("/api/pedidos") // La URL base para entrar a este controlador
public class PedidoController {

    private final PedidoService pedidoService; // Conexion con el cerebro de la app
    // Inyeccion por constructor del servicio

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping // Mapea las peticiones POST para registrar un nuevo pedido
    public ResponseEntity<?> registrarPedido(@RequestBody Pedido pedido) {
        try {
            // Intenta procesar la venta con las reglas de negocio y stock
            Pedido nuevoPedido = pedidoService.registrarPedido(pedido);
            // Si sale bien, devuelve el pedido creado con un estado 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);

        } catch (StockInsuficienteException | PedidoVacioException e) {
            // Si salta alguna de tus excepciones, atrapa el mensaje y devuelve un estado
            // 400 (Bad Request)
            // De esta forma el Frontend recibe el texto exacto del error para mostrarselo
            // al usuario
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            // atrapa cualquier otro error inesperado (Estado 500 - Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor.");
        }
    }

    @GetMapping // Mapea las peticiones GET para listar todos los pedidos
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos(); // El servicio busca la lista en la BD y el controlador la escupe en JSON
    }

    @GetMapping("/{id}") // Mapea las peticiones GET para buscar un pedido por su ID
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
        // Busca en la BD. Si lo encuentra devuelve 200 (OK), si no devuelve 404 (Not
        // Found)
        return pedidoService.buscarPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") // Recibe el ID del pedido que se quiere borrar
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id); // Llama al servicio para borrarlo de PostgreSQL
        return ResponseEntity.noContent().build(); // Devuelve estado 204 (Sin contenido) como señal de exito
    }

}

// 📝 AYUDAMEMORIA CONTROLADOR DE PEDIDOS Y MANEJO DE EXCEPCIONES:
//
// 1. ¿COMO SE CONECTA TU EXCEPCION ACA?
// El método 'registrarPedido' usa un bloque try-catch.Cuando el
// 'PedidoService' analiza
// los productos y detecta que no hay unidades en PostgreSQL, lanza
// 'StockInsuficienteException'.
// El Controller la atrapa en la línea 30 y frena el guardado.
//
// 2. ¿QUE ES EL COMODIN DE RETORNO 'ResponseEntity<?>'?
// El signo de pregunta '?' en Java es un (Generics). Significa que este
// metodo puede devolver
// "cualquier tipo de cuerpo" en la respuesta HTTP segun lo que pase:
// - Si sale BIEN: Devuelve un objeto de tipo 'Pedido' con estado 201 (Created).
// - Si sale MAL (catch): Devuelve un texto simple ('String') con el error y
// estado 400 (Bad Request).
//
// 3. ¿CÓMO VIAJA EL CARRITO DESDE EL FRONTEND?
// El Frontend manda un JSON estructurado con la informacion del pedido y una
// lista interna de objetos
// (los renglones del carrito). La etiqueta '@RequestBody' lee todo ese arbol de
// datos de un solo viaje,
// construye el objeto 'Pedido' completo en Java con sus 'LineaPedido' adentro,
// y se lo pasa al servicio.
