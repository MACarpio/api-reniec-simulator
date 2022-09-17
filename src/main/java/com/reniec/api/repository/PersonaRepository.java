package com.reniec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reniec.api.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

}
