package com.patronmvc.service.impl;

import com.patronmvc.exception.BusinessException;
import com.patronmvc.model.Persona;
import com.patronmvc.repository.PersonaRepository;
import com.patronmvc.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    private void validarCamposComunes(Persona persona) {
        if (persona.getId() == null) {
            throw new BusinessException("El documento de la persona no puede estar vacío");
        }

        if (String.valueOf(persona.getId()).length() <= 3) {
            throw new BusinessException("El documento de la persona debe ser mas de 3 digitos");
        }

        if (persona.getNombre() == null || persona.getNombre().trim().length() < 5) {
            throw new BusinessException("El nombre de la persona debe ser mayor a 5 digitos");
        }

        if (!persona.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,80}$")) {
            throw new BusinessException("El nombre solo puede contener letras y espacios");
        }

        if (persona.getProfesion() == null || !persona.getProfesion().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,120}$")) {
            throw new BusinessException("La profesión solo puede contener letras y espacios");
        }

        if (persona.getEdad() == null || persona.getEdad() <= 0) {
            throw new BusinessException("Edad no válida");
        }

        if (persona.getTelefono() == null) {
            throw new BusinessException("El número de teléfono es obligatorio");
        }

        int digitosTelefono = String.valueOf(persona.getTelefono()).length();
        if (digitosTelefono != 10) {
            throw new BusinessException("El número de teléfono solo debe tener 10 dígitos");
        }
    }

    @Override
    public Persona registrarPersona(Persona persona) {
        validarCamposComunes(persona);

        if (personaRepository.existsById(persona.getId())) {
            throw new BusinessException("El código ya se encuentra registrado");
        }

        return personaRepository.save(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> buscarPersona(Integer id) {
        if (id == null || String.valueOf(id).length() <= 3) {
            throw new BusinessException("El documento de la persona debe ser mas de 3 digitos");
        }
        return personaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Persona> listarPersonas() {
        return personaRepository.findAllByOrderByIdDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> listarCodigos() {
        return personaRepository.findAllIdsOrderByIdDesc();
    }

    @Override
    public Persona modificarPersona(Persona persona) {
        validarCamposComunes(persona);

        if (!personaRepository.existsById(persona.getId())) {
            throw new BusinessException("La persona con código " + persona.getId() + " no existe");
        }

        return personaRepository.save(persona);
    }

    @Override
    public void eliminarPersona(Integer id) {
        if (id == null) {
            throw new BusinessException("El código de la persona no puede ser nulo");
        }

        if (!personaRepository.existsById(id)) {
            throw new BusinessException("La persona a eliminar no existe");
        }

        personaRepository.deleteById(id);
    }
}
