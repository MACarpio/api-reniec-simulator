package com.reniec.api.Controller;

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
    private static final String INDEX ="persona/create"; 
    private static String MODEL_CONTACT="person";
    private final PersonaRepository personaData;

    public PersonaController(PersonaRepository personaData){
        this.personaData = personaData;
    }  

    @GetMapping("/persona/create")
    public String index(Model model) {
        model.addAttribute(MODEL_CONTACT, new Persona());
        return INDEX;
    }  

    @PostMapping("/persona/create")
    public String createSubmitForm(Model model, 
        @Valid Persona objPersona, BindingResult result ){
        if(result.hasFieldErrors()) {
            model.addAttribute("mensaje", "No se envio datos");
        }else{
            this.personaData.save(objPersona);
            model.addAttribute(MODEL_CONTACT, objPersona);
            model.addAttribute("mensaje", "Se envio datos");
        }
        return INDEX;
    }

}
