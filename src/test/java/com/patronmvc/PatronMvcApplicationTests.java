package com.patronmvc;

import com.patronmvc.exception.BusinessException;
import com.patronmvc.model.Persona;
import com.patronmvc.repository.PersonaRepository;
import com.patronmvc.service.impl.PersonaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatronMvcApplicationTests {

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PersonaServiceImpl personaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarPersonaValida() {
        Persona persona = new Persona(1001, "Carlos Gomez", 28, "Ingeniero", 3001234567L);
        when(personaRepository.existsById(1001)).thenReturn(false);
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);

        Persona registrada = personaService.registrarPersona(persona);
        assertNotNull(registrada);
        assertEquals(1001, registrada.getId());
        assertEquals("Carlos Gomez", registrada.getNombre());
    }

    @Test
    void testRegistrarPersonaDocumentoCorto() {
        Persona persona = new Persona(12, "Carlos Gomez", 28, "Ingeniero", 3001234567L);
        assertThrows(BusinessException.class, () -> personaService.registrarPersona(persona));
    }

    @Test
    void testRegistrarPersonaNombreInvalido() {
        Persona persona = new Persona(1001, "1234", 28, "Ingeniero", 3001234567L);
        assertThrows(BusinessException.class, () -> personaService.registrarPersona(persona));
    }

    @Test
    void testRegistrarPersonaTelefonoInvalido() {
        Persona persona = new Persona(1001, "Carlos Gomez", 28, "Ingeniero", 12345L);
        assertThrows(BusinessException.class, () -> personaService.registrarPersona(persona));
    }

    @Test
    void testBuscarPersonaExistente() {
        Persona persona = new Persona(1001, "Carlos Gomez", 28, "Ingeniero", 3001234567L);
        when(personaRepository.findById(1001)).thenReturn(Optional.of(persona));

        Optional<Persona> resultado = personaService.buscarPersona(1001);
        assertTrue(resultado.isPresent());
        assertEquals("Carlos Gomez", resultado.get().getNombre());
    }
}
