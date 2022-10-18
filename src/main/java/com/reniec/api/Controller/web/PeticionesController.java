package com.reniec.api.Controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.reniec.api.repository.PeticionesRepository;

import java.util.List;
import java.lang.Object;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PeticionesController {

    private final PeticionesRepository peticionesData;

    public PeticionesController(PeticionesRepository peticionesData) {
        this.peticionesData = peticionesData;
    }

    @GetMapping("/peticiones/")
    public String prueba(Model model, HttpServletRequest request) {
        model.addAttribute("user", request.getSession().getAttribute("user"));
        model.addAttribute("list", peticionesData.findCountPeticiones());
        return "peticiones/index";
    }

    @GetMapping("/peticionesjson/")
    public ResponseEntity<List<Object>> getPrueba() {

        return new ResponseEntity<List<Object>>(
                peticionesData.findCountPeticiones(), HttpStatus.OK);
    }
}
