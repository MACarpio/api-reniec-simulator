package com.reniec.api.Controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("listType", peticionesData.findCountPeticionesType());
        return "peticiones/index";
    }

    @PostMapping("/peticiones/")
    public String getPeticionesDni(Model model, String dni, HttpServletRequest request) {
        model.addAttribute("listdni", peticionesData.findCountPeticionesByDni(dni));
        model.addAttribute("user", request.getSession().getAttribute("user"));
        return "peticiones/dni";
    }

    @GetMapping("/peticionesjson/")
    public ResponseEntity<List<Object>> getPrueba() {

        return new ResponseEntity<List<Object>>(
                peticionesData.findCountPeticiones(), HttpStatus.OK);
    }
}
