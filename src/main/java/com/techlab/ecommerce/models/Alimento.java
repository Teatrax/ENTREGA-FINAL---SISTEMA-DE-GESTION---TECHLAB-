package com.techlab.ecommerce.models;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "alimentos") // Especifica el nombre de la tabla en la base de datos
public class Alimento extends Producto {

    // atributos específicos de alimento
    private LocalDate fechaCaducidad;

    // CONSTRUCTOR VACIO: Obligatorio para que funcione JPA
    public Alimento() {
        super();
    }

    // Constructor
    public Alimento(String nombre, Double precio, Integer stock, LocalDate fechaCaducidad) {
        super(nombre, precio, stock);

        // Validar que la fecha de caducidad no sea anterior a la fecha actual
        // isbefore fechaCaducidad es antes a LocalDate.now() fecha del sistema
        if (fechaCaducidad.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar un alimento vencido.");
        }
        this.fechaCaducidad = fechaCaducidad;
    }

    // getters
    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    // setters
    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return super.toString() + " | Fecha de Caducidad: " + fechaCaducidad;
    }

    // Mostrar detalles específicos de alimento
    @Override
    public void mostrarDetalles() {
        System.out.println(this.toString());
    }

}
