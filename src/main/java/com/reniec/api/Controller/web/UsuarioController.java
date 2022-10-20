package com.reniec.api.Controller.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.reniec.api.model.Usuario;
import com.reniec.api.repository.UsuarioRepository;

@Controller
public class UsuarioController {
    private static final String INDEX = "usuario/login";
    private static String MODEL_CONTACT = "user";
    private static String MODEL_MESSAGE = "mensaje";
    private final UsuarioRepository usuariosData;

    public UsuarioController(UsuarioRepository usuariosData) {
        this.usuariosData = usuariosData;
    }

    @GetMapping("/usuario/login")
    public String login(Model model) {
        model.addAttribute(MODEL_CONTACT, new Usuario());
        return INDEX;
    }

    @PostMapping("/usuario/login")
    public String loginSubmitForm(Model model,
            @Valid Usuario objUser,
            HttpServletRequest request,
            BindingResult result) {
        String page = INDEX;
        model.addAttribute(MODEL_CONTACT, new Usuario());
        if (result.hasFieldErrors()) {
            model.addAttribute(MODEL_MESSAGE, "No se ha podido loguear");
        } else {
            Optional<Usuario> userDB = this.usuariosData.findById(objUser.getUserID());

            if (userDB.isPresent()) {
                if (userDB.get().getVerify()) {
                    if (userDB.get().getPassword().equals(objUser.getPassword())) {
                        model.addAttribute(MODEL_CONTACT, userDB.get());
                        model.addAttribute(MODEL_MESSAGE, "Usuario existe");
                        request.getSession().setAttribute("user", objUser.getUserID());
                        page = "redirect:/";
                    } else {
                        model.addAttribute(MODEL_MESSAGE, "Password no coincide");
                    }
                } else {
                    model.addAttribute(MODEL_MESSAGE, "Usuario no verificado");
                }
            } else {
                model.addAttribute(MODEL_MESSAGE, "Usuario no existe");
            }
        }
        return page;
    }

    @GetMapping("/usuario/logout")
    public String logoutSession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/usuario/{token}")
    public String verifyToken(Model model, @PathVariable String token) {
        Optional<Usuario> userDB = this.usuariosData.findUsuarioByToken(token);
        if (userDB.isPresent()) {
            userDB.get().setVerify(true);
            this.usuariosData.save(userDB.get());
            model.addAttribute("mensaje", true);
        } else {
            model.addAttribute("mensaje", false);
        }
        return "usuario/verify";
    }
}
