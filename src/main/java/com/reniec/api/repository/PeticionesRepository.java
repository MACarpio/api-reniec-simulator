package com.reniec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.reniec.api.model.Peticiones;

@Repository
public interface PeticionesRepository extends JpaRepository<Peticiones, Long> {
    @Query(value = "SELECT userID as user, COUNT(*) as cantidad FROM Peticiones o GROUP BY userID ORDER BY COUNT(*) DESC")
    public abstract List<Object> findCountPeticiones();
}
