package com.reniec.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.reniec.api.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query(value = "SELECT o FROM Usuario o WHERE o.token=?1")
    Optional<Usuario> findUsuarioByToken(String token);
}
