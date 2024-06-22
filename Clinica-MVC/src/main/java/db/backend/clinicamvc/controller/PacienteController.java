package db.backend.clinicamvc.controller;

import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoController.class);

    public IPacienteService pacienteService;
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        LOGGER.info("Registrando nuevo paciente: {}", paciente);
        Paciente pacienteAretornar = pacienteService.registrarPaciente(paciente);
        if(pacienteAretornar == null){
            LOGGER.error("No se pudo registrar el paciente: {}", paciente);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LOGGER.info("Paciente registrado exitosamente: {}", pacienteAretornar);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteAretornar);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        LOGGER.info("Buscando todos los pacientes");
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id){
        LOGGER.info("Buscando paciente por ID: {}", id);
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if(paciente.isPresent()){
            LOGGER.info("Paciente encontrado: {}", paciente.get());
            return ResponseEntity.ok(paciente.get());
        }else{
            LOGGER.warn("Paciente no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        LOGGER.info("Actualizando paciente: {}", paciente);
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok("{\"message\": \"paciente no actualizado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        LOGGER.info("Borrando paciente con ID: {}", id);
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<List<Paciente>> buscarPorDni(@PathVariable String dni){
        LOGGER.info("Buscando paciente por DNI: {}", dni);
        return ResponseEntity.ok(pacienteService.buscarPorDni(dni));

    }

}

