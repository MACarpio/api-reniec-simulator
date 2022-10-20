package com.reniec.api.Controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reniec.api.model.Cliente;
import com.reniec.api.model.Usuario;
import com.reniec.api.repository.ClienteRepository;
import com.reniec.api.repository.UsuarioRepository;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class ClienteController {
    private static final String INDEX = "Cliente/create";
    private static String MODEL_CONTACT = "client";
    private final ClienteRepository clientsData;
    private final UsuarioRepository usuariosData;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("proyectosusmp22@gmail.com");
        message.setTo(to);
        message.setSubject("Verifica tu cuenta Reniec API");
        message.setText("Has click aqui para verificar tu cuenta -> https://app-reniec.herokuapp.com/usuario/" + text);
        emailSender.send(message);
    }

    public ClienteController(ClienteRepository clientsData,
            UsuarioRepository usuariosData) {
        this.clientsData = clientsData;
        this.usuariosData = usuariosData;
    }

    @GetMapping("/Cliente/create")
    public String index(Model model) {
        model.addAttribute(MODEL_CONTACT, new Cliente());
        return INDEX;
    }

    @PostMapping("/Cliente/create")
    public String createSubmitForm(Model model,
            @Valid Cliente objCliente, BindingResult result) {
        if (result.hasFieldErrors()) {
            model.addAttribute("mensaje", false);
        } else {
            Usuario user = objCliente.getUser();
            user.setTipoUsuario("C");
            user.setToken(UUID.randomUUID().toString());
            user.setVerify(false);
            this.usuariosData.save(user);
            this.usuariosData.flush();
            this.clientsData.save(objCliente);
            model.addAttribute(MODEL_CONTACT, objCliente);
            model.addAttribute("mensaje", true);
            sendSimpleMessage(objCliente.getEmail(), user.getToken());
        }
        return INDEX;
    }

}
