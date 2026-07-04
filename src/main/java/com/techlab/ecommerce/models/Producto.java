package com.techlab.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity // Le avisa a Spring que esto mapeara una tabla
@Table(name = "productos") // Define el nombre de la tabla en PostgreSQL
@Inheritance(strategy = InheritanceType.JOINED) // le dice a la Base de Datos que respete la herencia de Java.

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoProducto")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Alimento.class, name = "ALIMENTO"),
        @JsonSubTypes.Type(value = Bebida.class, name = "BEBIDA"),
        @JsonSubTypes.Type(value = Electronico.class, name = "ELECTRONICO")

// Le dice a Jackson (la libreria que lee/escribe JSON) que cuando reciba un
// "Producto" en un JSON mire el campo "tipoProducto" para saber si tiene que
// construir un Alimento, una Bebida o un Electronico. Sin esto Jackson no
// puede instanciar una clase abstracta y tira error al deserializar

})

public abstract class Producto {

    @Id // Define la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL maneja el id autoincremental

    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;

    // CONSTRUCTOR VACIO: Obligatorio para que funcione JPA
    public Producto() {
    }

    // Constructor
    public Producto(String nombre, Double precio, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    // setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    // Método toString para mostrar información del producto
    @Override
    public String toString() {
        // %-3d: El - alinea a la izquierda. El 3 reserva tres espacios. La d es para
        // "decimal"
        return String.format("ID: [%-3d] | %-20s | Precio: $%8.2f | Stock: %-5d", id, nombre, precio, stock);
    }

    // Método abstracto para mostrar detalles específicos de cada prodcuto
    public abstract void mostrarDetalles();

    // AYUDAMEMORIA JPA - HERENCIA (ESTRATEGIA JOINED):
    // Esta linea le dice a la Base de Datos que respete la herencia de Java.
    // Crea una tabla padre ("productos") con los campos comunes (id, nombre,
    // precio, stock)
    // y tablas hijas ("bebidas","alimentos") que se unen automaticamente mediante
    // el ID.
    // Evita tener una sola tabla gigante con campos vacíos (NULL), manteniendo la
    // base ordenada.

    // @Inheritance(strategy = InheritanceType.JOINED)

}
