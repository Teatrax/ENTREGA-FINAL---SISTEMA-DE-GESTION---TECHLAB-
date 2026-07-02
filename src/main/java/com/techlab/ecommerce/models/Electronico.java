package com.techlab.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "electronicos") // Especifica el nombre de la tabla en la base de datos
public class Electronico extends Producto {

    // atributos específicos de electrónico
    private String marca;
    private String modelo;
    private String mesesGarantia;

    // CONSTRUCTOR VACIO: Obligatorio para que funcione JPA
    public Electronico() {
        super();
    }

    // Constructor
    public Electronico(String nombre, Double precio, Integer stock, String marca, String modelo, String mesesGarantia) {
        super(nombre, precio, stock);
        this.marca = marca;
        this.modelo = modelo;
        this.mesesGarantia = mesesGarantia;
    }

    // getters
    public String getMarca() {
        return marca;
    }

    public String getMesesGarantia() {
        return mesesGarantia;
    }

    public String getModelo() {
        return modelo;
    }

    // setters
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setMesesGarantia(String mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public String toString() {
        return super.toString() + " | Marca: " + marca + " | Modelo: " + modelo + " | Meses de Garantía: "
                + mesesGarantia;
    }

    // Mostrar detalles específicos de electrónico
    @Override
    public void mostrarDetalles() {
        System.out.println(this.toString());
    }

}
