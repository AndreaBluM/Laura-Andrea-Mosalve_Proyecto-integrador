package db.backend.clinicamvc.controller;

import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")

public class PacienteController {
    public IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteAretornar = pacienteService.registrarPaciente(paciente);
        if(pacienteAretornar == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteAretornar);
        }

    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
        public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id){
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if(paciente.isPresent()){
            return ResponseEntity.ok(paciente.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok("{\"message\": \"paciente no actualizado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<List<Paciente>> buscarPorDni(@PathVariable String dni){
        return ResponseEntity.ok(pacienteService.buscarPorDni(dni));
    }
}
