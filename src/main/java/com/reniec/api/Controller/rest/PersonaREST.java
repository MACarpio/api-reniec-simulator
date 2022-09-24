package com.reniec.api.Controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reniec.api.model.Persona;
import com.reniec.api.repository.PersonaRepository;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/persona", produces = "application/json")
public class PersonaREST {
    private final PersonaRepository personaData;

    public PersonaREST(PersonaRepository personaData) {
        this.personaData = personaData;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Persona>> getPersonas() {
        return new ResponseEntity<List<Persona>>(
                personaData.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPersona(@RequestBody Persona persona) {
        personaData.save(persona);
        personaData.flush();
        return new ResponseEntity<String>(persona.getDni(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> getPersona(@PathVariable String dni) {
        Optional<Persona> optinalEntity = personaData.findById(dni);
        if (optinalEntity.isPresent())
            return new ResponseEntity<Persona>(
                    optinalEntity.get(), HttpStatus.OK);
        else
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePersona(@PathVariable String dni) {
        personaData.deleteById(dni);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePersona(@PathVariable String dni, @RequestBody Persona persona) {
        Persona obj = new Persona();
        obj.setDni(dni);
        obj.setNombres(persona.getNombres());
        obj.setApePat(persona.getApePat());
        obj.setApeMat(persona.getApeMat());
        obj.setDirec(persona.getDirec());
        obj.setGenero(persona.getGenero());
        obj.setBirthdate(persona.getBirthdate());
        obj.setEstado(persona.getEstado());
        personaData.save(obj);
        return new ResponseEntity(HttpStatus.OK);
    }

}
