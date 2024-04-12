package com.cumple.BE.controller;

import com.cumple.BE.Persona;
import com.cumple.BE.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/confirmados")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if(persona.isPresent()) {
            return ResponseEntity.ok(persona.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Persona> confirmarInvitacion(@RequestBody Persona persona) {
        Persona savedPersona = personaRepository.save(persona);
        return ResponseEntity.ok(savedPersona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona detallesPersona) {
        Optional<Persona> personaActual = personaRepository.findById(id);
        if(personaActual.isPresent()) {
            Persona _persona = personaActual.get();
            _persona.setNombre(detallesPersona.getNombre());
            _persona.setApellido(detallesPersona.getApellido());
            _persona.setDni(detallesPersona.getDni());
            return ResponseEntity.ok(personaRepository.save(_persona));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable Long id) {
        try {
            personaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se pudo eliminar la persona");
        }
    }
}
