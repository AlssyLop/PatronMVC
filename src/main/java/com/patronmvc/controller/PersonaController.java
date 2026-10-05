package com.patronmvc.controller;

import com.patronmvc.exception.BusinessException;
import com.patronmvc.model.Persona;
import com.patronmvc.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public String index(Model model) {
        List<Persona> personas = personaService.listarPersonas();
        model.addAttribute("personas", personas);
        return "index";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("persona")) {
            model.addAttribute("persona", new Persona());
        }
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrarPersona(@ModelAttribute("persona") Persona persona,
                                   RedirectAttributes redirectAttributes) {
        try {
            personaService.registrarPersona(persona);
            redirectAttributes.addFlashAttribute("mensajeExito", "Se ha registrado Exitosamente");
            return "redirect:/personas";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            redirectAttributes.addFlashAttribute("persona", persona);
            return "redirect:/personas/registrar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo registrar la persona: " + e.getMessage());
            redirectAttributes.addFlashAttribute("persona", persona);
            return "redirect:/personas/registrar";
        }
    }

    @GetMapping("/buscar")
    public String mostrarVistaBuscar(@RequestParam(name = "codigo", required = false) Integer codigo,
                                     Model model) {
        List<Integer> codigos = personaService.listarCodigos();
        model.addAttribute("codigos", codigos);
        model.addAttribute("codigoBuscado", codigo);

        if (codigo != null) {
            try {
                Optional<Persona> personaOpt = personaService.buscarPersona(codigo);
                if (personaOpt.isPresent()) {
                    model.addAttribute("personaEncontrada", personaOpt.get());
                } else {
                    model.addAttribute("mensajeInfo", "No existe una persona con el código " + codigo);
                }
            } catch (BusinessException e) {
                model.addAttribute("mensajeError", e.getMessage());
            } catch (Exception e) {
                model.addAttribute("mensajeError", "Error al consultar: " + e.getMessage());
            }
        }

        return "buscar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        try {
            Optional<Persona> personaOpt = personaService.buscarPersona(id);
            if (personaOpt.isPresent()) {
                model.addAttribute("persona", personaOpt.get());
                return "editar";
            } else {
                redirectAttributes.addFlashAttribute("mensajeError", "La persona no fue encontrada");
                return "redirect:/personas";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/personas";
        }
    }

    @PostMapping("/editar")
    public String modificarPersona(@ModelAttribute("persona") Persona persona,
                                   RedirectAttributes redirectAttributes) {
        try {
            personaService.modificarPersona(persona);
            redirectAttributes.addFlashAttribute("mensajeExito", "Se ha Modificado Correctamente");
            return "redirect:/personas";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/personas/editar/" + persona.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al Modificar: " + e.getMessage());
            return "redirect:/personas/editar/" + persona.getId();
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable("id") Integer id,
                                  RedirectAttributes redirectAttributes) {
        try {
            personaService.eliminarPersona(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Se ha Eliminado Correctamente");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/personas";
    }
}
