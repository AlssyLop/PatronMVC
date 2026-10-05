package com.patronmvc.service;

import com.patronmvc.model.Persona;
import java.util.List;
import java.util.Optional;

public interface PersonaService {

    Persona registrarPersona(Persona persona);

    Optional<Persona> buscarPersona(Integer id);

    List<Persona> listarPersonas();

    List<Integer> listarCodigos();

    Persona modificarPersona(Persona persona);

    void eliminarPersona(Integer id);
}
