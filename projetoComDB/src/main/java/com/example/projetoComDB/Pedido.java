package com.example.projetoComDB;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TB_PEDIDO")

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne //Relacionamento de muitos para um com User
    private User user;

    @OneToMany(mappedBy = "pedido")
    @JsonManagedReference
    private List<Item> itemList = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    /*CONSTRUTORES*/

    public Pedido (){

    }

    public Pedido (User user){
        this.user = user;
    }

    public Pedido (String name, User user) {
        this.name = name;
        this.user = user;
    }


    public void addItem(Item item) {
        this.itemList.add(item);
    }

    public void removeItem (Item item) {
        this.itemList.remove(item);
    }
}
