package com.reniec.api.Controller.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reniec.api.repository.PersonaRepository;
import com.reniec.api.repository.UsuarioRepository;

import com.reniec.api.model.Persona;

@Controller
public class HomeController {
    private final PersonaRepository personaData;
    private final UsuarioRepository usuarioData;
    private static final String HOME_INDEX = "welcome";
    private static final String MODEL_PERSON = "persona";

    public HomeController(PersonaRepository personaData, UsuarioRepository usuarioData) {
        this.personaData = personaData;
        this.usuarioData = usuarioData;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute(MODEL_PERSON, new Persona());
        return HOME_INDEX;
    }

    @PostMapping("/")
    public String indexPost(Model model, String dni) {
        try {
            Optional<Persona> persona = personaData.findById(dni);
            if (persona.isPresent()) {
                model.addAttribute(MODEL_PERSON, persona.get());
                model.addAttribute("mensaje", true);
            } else {
                model.addAttribute(MODEL_PERSON, persona.get());
                model.addAttribute("mensaje", false);
            }
        } catch (Exception e) {
            model.addAttribute(MODEL_PERSON, new Persona());
            model.addAttribute("mensaje", false);
        }

        return HOME_INDEX;
    }
}
