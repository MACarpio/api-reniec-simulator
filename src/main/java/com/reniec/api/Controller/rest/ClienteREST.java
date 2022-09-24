package com.reniec.api.Controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reniec.api.model.Cliente;
import com.reniec.api.model.Persona;
import com.reniec.api.repository.ClienteRepository;
import com.reniec.api.repository.PersonaRepository;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/cliente", produces = "application/json")
public class ClienteREST {
    private final ClienteRepository clienteData;

    public ClienteREST(ClienteRepository clienteData) {
        this.clienteData = clienteData;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> getPersonas() {
        return new ResponseEntity<List<Cliente>>(
                clienteData.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> create(@RequestBody Cliente e) {
        clienteData.save(e);
        clienteData.flush();
        return new ResponseEntity<Integer>(e.getId(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> Productos(@PathVariable Integer id) {
        Optional<Cliente> optinalEntity = clienteData.findById(id);
        if (optinalEntity.isPresent())
            return new ResponseEntity<Cliente>(
                    optinalEntity.get(), HttpStatus.OK);
        else
            return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Integer id) {
        clienteData.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
