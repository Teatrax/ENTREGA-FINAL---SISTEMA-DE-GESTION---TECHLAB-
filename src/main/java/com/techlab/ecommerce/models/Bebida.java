package com.techlab.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "bebidas")
public class Bebida extends Producto {

    // atributos específicos de bebida
    @Enumerated(EnumType.STRING) // Guarda el texto (ej: "REFRESCO") en la columna SQL
    private TipoBebida tipo;

    private Integer millilitros;

    // CONSTRUCTOR VACIO: Obligatorio para que funcione JPA
    public Bebida() {
        super();
    }

    // Constructor
    public Bebida(String nombre, Double precio, Integer stock, TipoBebida tipo, Integer millilitros) {
        super(nombre, precio, stock);
        this.tipo = tipo;
        this.millilitros = millilitros;
    }

    // getters
    public TipoBebida getTipo() {
        return tipo;
    }

    public Integer getMillilitros() {
        return millilitros;
    }

    // setters
    public void setTipo(TipoBebida tipo) {
        this.tipo = tipo;
    }

    public void setMillilitros(Integer millilitros) {
        this.millilitros = millilitros;
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo: " + tipo + " | Millilitros: " + millilitros;
    }

    // Mostrar detalles específicos de bebida
    @Override
    public void mostrarDetalles() {
        System.out.println(this.toString());
    }

}
