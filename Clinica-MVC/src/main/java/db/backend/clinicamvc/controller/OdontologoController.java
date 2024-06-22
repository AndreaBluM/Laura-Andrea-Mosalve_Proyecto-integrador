package db.backend.clinicamvc.controller;

import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.impl.OdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoController.class);
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        LOGGER.info("Registrando odontólogo: " + odontologo.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.agregarOdontologo(odontologo));
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id){
        LOGGER.info("Buscando odontólogo por ID: {}", id);
        Optional<Odontologo> odontologo = odontologoService.buscarUnOdontologo(id);
        if(odontologo.isPresent()){
            Odontologo odontologoARetornar = odontologo.get();
            LOGGER.info("Odontólogo encontrado: {}", odontologoARetornar);
            return ResponseEntity.ok(odontologoARetornar);
        } else {
            LOGGER.warn("Odontólogo no encontrado para ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOpcional = odontologoService.buscarUnOdontologo(odontologo.getId());
        if (odontologoOpcional.isPresent()){
            odontologoService.modificarOdontologo(odontologo);
            return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        return ResponseEntity.ok(odontologoService.buscarPorApellido(apellido));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}
