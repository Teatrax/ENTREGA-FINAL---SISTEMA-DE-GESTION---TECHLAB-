package com.techlab.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlab.ecommerce.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}

// AYUDAMEMORIA JPA - REPOSITORY:
// interface + extends JpaRepository: Le pide a Spring que fabrique la lógica de
// la BD por atrás.
// <Pedido, Integer>: Maneja la entidad "Pedido" y su clave primaria (ID) es de
// tipo "Integer".
// Nos regala métodos automáticos listos para usar: .save(), .findAll(),
// .findById(), .deleteById().