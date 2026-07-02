package com.techlab.ecommerce.models;

import com.techlab.ecommerce.interfaces.Facturable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido implements Facturable {

    @Id // Indica que este atributo es la clave primaria de la tabla "pedidos"
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente un valor único para el ID al insertar
                                                        // un nuevo pedido en la BD
    private Integer idPedido;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Relación uno a muchos con LineaPedido
    @JoinColumn(name = "pedido_id") // Crea la columna de union en la tabla "lineas_pedido"
    private List<LineaPedido> lineasPedido;

    @Enumerated(EnumType.STRING) // Guarda el estado como texto ("PENDIENTE", "ENTREGADO") en SQL
    private EstadoPedido estado;

    // Constructor
    public Pedido() {

        this.lineasPedido = new ArrayList<>();
        this.estado = EstadoPedido.PENDIENTE; // Todo pedido nace pendiente
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public List<LineaPedido> getLineasPedido() {
        return this.lineasPedido;
    }

    // agrega una línea al pedido
    public void agregarLinea(LineaPedido linea) {
        this.lineasPedido.add(linea);
    }

    // calcula el total de toda la compra
    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineasPedido) {
            total += linea.calcularSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append(String.format("PEDIDO Nro: [%03d] | Estado: %s\n", idPedido, estado));
        sb.append("----------------------------------------\n");

        // Recorremos las líneas y cada una aporta su toString()
        for (LineaPedido linea : lineasPedido) {
            sb.append(linea.toString()).append("\n");
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL DEL PEDIDO: $%10.2f\n", calcularTotal()));
        sb.append("========================================");

        return sb.toString();
    }

    public void mostrarResumenPedido() {
        System.out.println(this.toString());
    }

    // AYUDAMEMORIA JPA - RELACIÓN UNO A MUCHOS (PEDIDO -> LINEAS):
    // @OneToMany: Un pedido puede tener muchas líneas de productos en el carrito.
    // cascade = ALL: Si guardo/borro el pedido, se guardan/borran sus líneas
    // automáticamente en la BD.
    // fetch = LAZY: Carga perezosa. No trae los productos de la BD a menos que use
    // .getLineasPedido() (ahorra memoria).
    // @JoinColumn: Clava una columna "pedido_id" en la tabla "lineas_pedido" para
    // saber a que pedido pertenece cada renglón.

}
