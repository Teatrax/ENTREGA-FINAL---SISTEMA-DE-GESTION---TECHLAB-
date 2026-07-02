# 🛒 Sistema de Gestion de E-Commerce

Bienvenido al sistema de **E-Commerce** desarrollado en Java con Spring Boot y PostgreSQL. Este proyecto fue diseñado aplicando los principios de la **Programación Orientada a Objetos (POO)** y las mejores practicas del desarrollo de software profesional en el ecosistema backend de Java.

El sistema simula un flujo completo de ventas, controlando el inventario renglon por renglon en tiempo real y persistiendo los datos de manera permanente.

---

## 🛠️ Tecnologías y Herramientas Utilizadas

* **Lenguaje:** Java 21
* **Framework Principal:** Spring Boot (Spring Data JPA, Spring Web)
* **Base de Datos:** PostgreSQL (Motor Relacional)
* **Persistencia y ORM:** Hibernate
* **Administrador de BD:** DBeaver (para la gestión y visualización de tablas)
* **IDE de Desarrollo:** Visual Studio Code (VS Code)

---

## 🧩 Características Claves del Proyecto

### 1. Modelo de Datos (Herencia y Polimorfismo)
Se implementó una estructura de herencia para los productos utilizando la estrategia `@Inheritance(strategy = InheritanceType.JOINED)`. Esto permite tener una tabla padre común (`productos`) y tablas hijas específicas con sus propios atributos:
* **Alimentos:** Maneja `fechaCaducidad`.
* **Bebidas:** Maneja `millilitros` y `esAlcoholica`.
* **Electrónicos:** Maneja `marca`, `modelo` y `mesesGarantia`.

### 2. Capa de Servicios
La lógica de negocio se encuentra centralizada en la capa de servicios (`@Service`). Se aplicó la **Inyección por Constructor** (descartando la inyección directa por atributo `@Autowired` en variables), asegurando la inmutabilidad de los componentes mediante la palabra clave `final`.

### 3. Circuito Transaccional Seguro y Control de Stock
El metodo de registro de pedidos realiza una validación de stock:
* Recorre cada linea del pedido enviado por el cliente.
* Compara la cantidad solicitada con el stock real remanente en PostgreSQL.
* Si el stock es insuficiente, interrumpe inmediatamente la operacion lanzando una excepción personalizada (`StockInsuficienteException`), impidiendo que se guarden datos corruptos o inconsistentes.
* Si hay stock disponible, lo descuenta, actualiza el producto e impacta el pedido completo en la base de datos (gracias a `CascadeType.ALL`).

### 4. Controlador RESTful e Interfaz Web
Los controladores (`@RestController`) exponen endpoints limpios que responden en formato **JSON** mediante metodos HTTP (`GET`, `POST`, `DELETE`). Además, se habilitó `@CrossOrigin(origins = "*")` para permitir el consumo seguro de la API desde un cliente Frontend independiente.

---
## 📂 Estructura del Proyecto (Arquitectura en Capas)

```text
src/main/java/com/techlab/ecommerce/
│
├── controller/          # Capa de Entrada: Recibe peticiones HTTP (JSON) y gestiona respuestas (ResponseEntity).
├── service/             # Capa de Negocio: Contiene los algoritmos de control de stock y reglas del sistema.
├── repository/          # Capa de Datos: Interfaces que extienden JpaRepository para consultas automáticas a SQL.
├── models/              # Capa del Dominio: Entidades mapeadas a tablas relacionales de PostgreSQL.
└── exception/           # Capa de Errores: Excepciones de negocio personalizadas (StockInsuficienteException, PedidoVacioException).