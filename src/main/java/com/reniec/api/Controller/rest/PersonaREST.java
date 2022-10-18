package com.reniec.api.Controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reniec.api.model.Persona;
import com.reniec.api.model.Peticiones;
import com.reniec.api.model.Usuario;
import com.reniec.api.repository.PersonaRepository;
import com.reniec.api.repository.PeticionesRepository;
import com.reniec.api.repository.UsuarioRepository;

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
import java.sql.Date;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/persona", produces = "application/json")
public class PersonaREST {
    private final PersonaRepository personaData;
    private final UsuarioRepository usuariosData;
    private final PeticionesRepository peticionesData;

    public PersonaREST(PersonaRepository personaData, UsuarioRepository usuariosData,
            PeticionesRepository peticionesData) {
        this.personaData = personaData;
        this.usuariosData = usuariosData;
        this.peticionesData = peticionesData;
    }

    public Optional<Usuario> VerificarToken(String token) {
        Optional<Usuario> userDB = this.usuariosData.findUsuarioByToken(token);
        return userDB;
    }

    public String ObtenerUsuario(String token) {
        Optional<Usuario> user = this.usuariosData.findById(token);
        return user.get().getUserID();
    }

    public void RegistrarPeticion(String dni, String apiKey, String type) {
        Peticiones peticion = new Peticiones();
        peticion.setDni(dni);
        peticion.setType(type);
        peticion.setUserID(VerificarToken(apiKey).get().getUserID());
        peticion.setFechaRegistro(Date.valueOf(java.time.LocalDate.now()));
        peticionesData.save(peticion);
    }

    @GetMapping(value = "/apiKey={apiKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Persona>> getPersonas(@PathVariable String apiKey) {
        if (VerificarToken(apiKey).isPresent()) {
            RegistrarPeticion("All", apiKey, "GET");
            return new ResponseEntity<List<Persona>>(
                    personaData.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<List<Persona>>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/apiKey={apiKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPersona(@RequestBody Persona persona, @PathVariable String apiKey) {
        if (VerificarToken(apiKey).isPresent()) {
            RegistrarPeticion(persona.getDni(), apiKey, "POST");
            personaData.save(persona);
            personaData.flush();
            return new ResponseEntity<String>(persona.getDni(), HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(
                HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/dni={dni}&apiKey={apiKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> getPersona(@PathVariable String dni, @PathVariable String apiKey) {
        if (VerificarToken(apiKey).isPresent()) {
            Optional<Persona> optinalEntity = personaData.findById(dni);
            if (optinalEntity.isPresent()) {
                RegistrarPeticion(dni, apiKey, "GET");
                return new ResponseEntity<Persona>(
                        optinalEntity.get(), HttpStatus.OK);
            } else
                return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Persona>(
                HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping(value = "/dni={dni}&apiKey={apiKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePersona(@PathVariable String dni, @PathVariable String apiKey) {
        if (VerificarToken(apiKey).isPresent()) {
            RegistrarPeticion(dni, apiKey, "DELETE");
            personaData.deleteById(dni);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/dni={dni}&apiKey={apiKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePersona(@PathVariable String dni, @RequestBody Persona persona,
            @PathVariable String apiKey) {
        if (VerificarToken(apiKey).isPresent()) {
            Persona obj = new Persona();
            obj.setDni(dni);
            obj.setNombres(persona.getNombres());
            obj.setApePat(persona.getApePat());
            obj.setApeMat(persona.getApeMat());
            obj.setDirec(persona.getDirec());
            obj.setGenero(persona.getGenero());
            obj.setBirthdate(persona.getBirthdate());
            obj.setEstado(persona.getEstado());
            obj.setUbigeo(persona.getUbigeo());
            obj.setFecEmision(persona.getFecEmision());
            obj.setFecCaduc(persona.getFecCaduc());
            personaData.save(obj);
            RegistrarPeticion(dni, apiKey, "PUT");
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
