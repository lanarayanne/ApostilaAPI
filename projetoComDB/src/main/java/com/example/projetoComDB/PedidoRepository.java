package com.example.projetoComDB;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
//Permite realizar operações CRUD

