package com.example.projetoComDB;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="TB_ITENS")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



    public Item () {
    }

    public Item (String name, float price) {
        this.name = name;
        this.price = price;
    }

    @ManyToOne
    @JsonBackReference
    private Pedido pedido;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido; // Associa o pedido ao item
    }
}
