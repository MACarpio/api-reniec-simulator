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

}
