package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlab.ecommerce.models.Alimento;
import com.techlab.ecommerce.models.Bebida;
import com.techlab.ecommerce.models.Electronico;
import com.techlab.ecommerce.models.Producto;
import com.techlab.ecommerce.service.ProductoService;

@RestController // Avisa a Spring que esta clase maneja peticiones HTTP y responde en JSON
@CrossOrigin(origins = "*")
@RequestMapping("/api/productos") // La URL base para entrar a este controlador
public class ProductoController {

    private final ProductoService productoService; // Conexión con el cerebro de la app

    // Inyeccion por constructor del servicio
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping // Mapea las peticiones GET a la ruta base
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos(); // El servicio los busca en la BD y los escupe en JSON
    }

    @GetMapping("/{id}") // El {id} es una variable dinamica que viene en la URL
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        // Busca el producto. Si existe lo devuelve con estado 200 (OK), sino devuelve
        // 404 (Not Found)
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/alimentos") // Endpoint especifico para registrar alimentos
    public Producto guardarAlimento(@RequestBody Alimento alimento) {
        return productoService.guardarProducto(alimento); // El JSON se convierte en objeto Alimento y se guarda
    }

    @PostMapping("/bebidas") // Endpoint especifico para registrar bebidas
    public Producto guardarBebida(@RequestBody Bebida bebida) {
        return productoService.guardarProducto(bebida); // El JSON se convierte en objeto Bebida y se guarda
    }

    @PostMapping("/electronicos") // Endpoint especifico para registrar electrónicos
    public Producto guardarElectronico(@RequestBody Electronico electronico) {
        return productoService.guardarProducto(electronico); // El JSON se convierte en objeto Electronico y se guarda
    }

    @DeleteMapping("/{id}") // Recibe el ID del producto que se quiere borrar
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id); // Llama al servicio para borrarlo de PostgreSQL
        return ResponseEntity.noContent().build(); // Devuelve un estado 204 (Sin contenido) indicando exito
    }

}

// AYUDAMEMORIA CAPA DE CONTROLADORES (REST CONTROLLER LAYER):
//
// 1. ¿QUE HACE @RestController Y @RequestMapping?
// @RestController le dice a Spring que todo lo que devuelvan estos metodos debe
// transformarse
// automáticamente a formato JSON (el texto que entiende el Frontend).
// @RequestMapping("/api/productos")
// asigna la "dirección web" oficial. Si tu app corre local, la puerta de
// entrada es: http://localhost:8080/api/productos.
//
// 2. ¿COMO SE RECIBEN LOS DATOS DEL FRONTEND?
// - @PathVariable: Captura valores que vienen directo en la URL.
// - @RequestBody:Captura el cuerpo de la petición HTTP (el JSON
// enviado por el Frontend con los
// datos del producto y lo desarma para construir automaticamente un objeto
// Java listo para usar.
//
// 3. MANEJO DE LA HERENCIA EN LOS ENDPOINTS:
// Como 'Producto' es una clase abstracta, el Frontend no puede crear un
// "Producto" a secas. Por eso se crearon
// rutas hijas específicas (/alimentos, /bebidas, /electronicos). El Frontend
// golpea la ruta que corresponde,
// pero nosotros adentro usamos el mismo 'productoService.guardarProducto()',
// aprovechando el polimorfismo.
//
// 4. ¿QUÉ ES EL 'ResponseEntity'?
// Es una herramienta profesional de Spring que nos permite controlar el "Estado
// HTTP" de la respuesta.
// No solo devuelve los datos, sino que avisa al navegador si todo salió bien
// (200 OK), si no se encontro (404 Not Found),
// o si se borro con exito (204 No Content). Esto le sirve al Frontend para
// saber que cartel mostrarle al usuario.