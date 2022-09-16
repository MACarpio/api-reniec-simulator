package com.reniec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reniec.api.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, String> {
    
}
