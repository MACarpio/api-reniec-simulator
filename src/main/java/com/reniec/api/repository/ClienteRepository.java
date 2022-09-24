package com.reniec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.reniec.api.model.Cliente;
import com.reniec.api.model.Usuario;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Integer> {

    @Query(value = "SELECT o FROM Cliente o WHERE o.user=?1")
    Cliente findByUsuario(Usuario user);
    
}
