//Servicio de Inventario básico (Crear, Leer, Actualizar, Borrar).
package com.techlab.ecommerce.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.techlab.ecommerce.models.Producto;
import com.techlab.ecommerce.repository.ProductoRepository;

@Service // Le avisa a Spring que este es el "cerebro" de la lógica de negocio
public class ProductoService {

    // private final para asegurar que el repositorio sea inmutable
    private final ProductoRepository productoRepository;

    // Inyeccion por constructor del repositorio
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto); // Guarda o actualiza directo en PostgreSQL
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll(); // Busca todos los productos en la BD y los devuelve en una lista
    }

    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id); // Busca por clave primaria
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id); // Borra el producto por su ID
    }
}