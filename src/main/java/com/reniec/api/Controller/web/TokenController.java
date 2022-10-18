package com.reniec.api.Controller.web;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;
import com.reniec.api.model.Usuario;
import com.reniec.api.repository.UsuarioRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TokenController {
    private final UsuarioRepository usuariosData;

    public TokenController(UsuarioRepository usuariosData) {
        this.usuariosData = usuariosData;
    }

    @GetMapping("/token/")
    public String prueba(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            model.addAttribute("token", "No hay usuario");
        } else {
            Optional<Usuario> userDB = this.usuariosData.findById(request.getSession().getAttribute("user").toString());
            model.addAttribute("token", userDB.get().getToken());
        }
        return "token/index";
    }
}
