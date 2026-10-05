package com.patronmvc.repository;

import com.patronmvc.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    List<Persona> findAllByOrderByIdDesc();

    @Query("SELECT p.id FROM Persona p ORDER BY p.id DESC")
    List<Integer> findAllIdsOrderByIdDesc();
}
