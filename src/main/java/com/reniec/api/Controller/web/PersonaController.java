package com.reniec.api.Controller.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reniec.api.model.Persona;
import com.reniec.api.repository.PersonaRepository;

@Controller
public class PersonaController {
    private static final String INDEX = "Persona/create";
    private static String MODEL_CONTACT = "person";
    private final PersonaRepository personaData;

    public PersonaController(PersonaRepository personaData) {
        this.personaData = personaData;
    }

    @GetMapping("/Persona/create")
    public String index(Model model) {
        model.addAttribute(MODEL_CONTACT, new Persona());
        return INDEX;
    }

    @PostMapping("/Persona/create")
    public String createSubmitForm(Model model,
            @Valid Persona objPersona, BindingResult result) {
        if (result.hasFieldErrors()) {
            model.addAttribute("mensaje", false);
        } else {
            this.personaData.save(objPersona);
            model.addAttribute(MODEL_CONTACT, new Persona());
            model.addAttribute("mensaje", true);
        }
        return INDEX;
    }

}
