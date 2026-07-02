package com.techlab.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Entity // Le avisa a Spring que esto mapeara una tabla
@Table(name = "lineas_pedido") // Define el nombre de la tabla en PostgreSQL
public class LineaPedido {

    @Id // Define la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL maneja el id autoincremental
    private Long id;

    @ManyToOne // Muchas líneas pueden tener el mismo producto
    @JoinColumn(name = "producto_id") // Nombre de la columna de clave foránea en SQL
    private Producto producto;

    private Integer cantidad;

    // constructor
    public LineaPedido(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // constructor vacio
    public LineaPedido() {
    }

    // Getters and Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        // Mostramos el nombre del producto, la cantidad y el subtotal calculado
        return String.format("%-20s x %-3d unidades | Subtotal: $%8.2f",
                producto.getNombre(), cantidad, calcularSubtotal());
    }

    public void mostrarDetalles() {
        System.out.println(this.toString());
    }

}
