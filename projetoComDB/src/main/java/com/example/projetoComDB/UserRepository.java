package com.example.projetoComDB;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
//Permite realizar operações CRUD