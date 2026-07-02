package com.techlab.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlab.ecommerce.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}

// AYUDAMEMORIA JPA - REPOSITORY:
// interface + extends JpaRepository: Le pide a Spring que fabrique la lógica de
// la BD por atrás.
// <Producto, Integer>: Maneja la entidad "Producto" y su clave primaria (ID) es
// de tipo "Integer".
// Nos regala metodos automaticos listos para usar: .save(), .findAll(),
// .findById(), .deleteById().