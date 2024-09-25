package com.example.projetoComDB;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity // A classe será gravada no banco de dados
@Table(name="TB_USER")

public class User {

    @Id // Id como chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) //Id gerado automaticamente

    private Long id;
    private String name;
    private String password;
    private String email;

    /*GETTERS E SETTERS*/

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail (){
        return email;
    }
    public void setEmail (String email){
        this.email = email;
    }

    /*CONSTRUTORES*/

    public User (){

    }

    public User (String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    @OneToMany(mappedBy = "user")
    private List<Pedido> PedidoList = new ArrayList<>();






}
