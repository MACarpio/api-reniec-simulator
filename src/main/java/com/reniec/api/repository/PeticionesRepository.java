package com.reniec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reniec.api.model.Peticiones;

@Repository
public interface PeticionesRepository extends JpaRepository<Peticiones, Long> {

}
