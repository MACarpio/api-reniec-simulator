package com.reniec.api.Controller.web;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reniec.api.model.Persona;
import com.reniec.api.model.Peticiones;
import com.reniec.api.repository.PersonaRepository;
import com.reniec.api.repository.PeticionesRepository;

@Controller
public class PersonaController {
    private static final String INDEX = "Persona/create";
    private static String MODEL_CONTACT = "person";
    private final PersonaRepository personaData;
    private final PeticionesRepository peticionesData;

    public PersonaController(PersonaRepository personaData, PeticionesRepository peticionesData) {
        this.personaData = personaData;
        this.peticionesData = peticionesData;
    }

    public void RegistrarPeticion(String dni, String user, String type) {
        Peticiones peticion = new Peticiones();
        peticion.setDni(dni);
        peticion.setType(type);
        peticion.setUserID(user);
        peticion.setFechaRegistro(Date.valueOf(java.time.LocalDate.now()));
        peticionesData.save(peticion);
    }

    @GetMapping("/Persona/create")
    public String index(Model model) {
        model.addAttribute(MODEL_CONTACT, new Persona());
        return INDEX;
    }

    @PostMapping("/Persona/create")
    public String createSubmitForm(Model model,
            @Valid Persona objPersona, BindingResult result, HttpServletRequest request) {
        if (result.hasFieldErrors()) {
            model.addAttribute("mensaje", false);
        } else {
            this.personaData.save(objPersona);
            RegistrarPeticion(objPersona.getDni(), request.getSession().getAttribute("user").toString(), "POST");
            model.addAttribute(MODEL_CONTACT, new Persona());
            model.addAttribute("mensaje", true);
        }
        return INDEX;
    }

}
