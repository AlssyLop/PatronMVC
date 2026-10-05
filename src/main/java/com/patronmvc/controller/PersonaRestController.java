package com.patronmvc.controller;

import com.patronmvc.model.Persona;
import com.patronmvc.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaRestController {

    private final PersonaService personaService;

    @Autowired
    public PersonaRestController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarTodas() {
        return ResponseEntity.ok(personaService.listarPersonas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable("id") Integer id) {
        return personaService.buscarPersona(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigos")
    public ResponseEntity<List<Integer>> listarCodigos() {
        return ResponseEntity.ok(personaService.listarCodigos());
    }

    @PostMapping
    public ResponseEntity<Persona> registrar(@RequestBody Persona persona) {
        Persona guardada = personaService.registrarPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable("id") Integer id, @RequestBody Persona persona) {
        persona.setId(id);
        Persona actualizada = personaService.modificarPersona(persona);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}
