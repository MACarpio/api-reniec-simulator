package com.reniec.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.reniec.api.model.Persona;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

    @Query(value = "SELECT o FROM Producto o WHERE o.status='A'")
    List<Persona> getAllActiveProductos();

    @Query(value = "SELECT o FROM Producto o WHERE o.descripcion LIKE  %:searchName% And o.status='A'")
    List<Persona> getAllActiveProductosBySearch(@Param("searchName") String searchName);

}
