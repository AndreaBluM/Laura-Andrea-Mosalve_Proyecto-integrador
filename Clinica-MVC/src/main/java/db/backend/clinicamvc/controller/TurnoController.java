package db.backend.clinicamvc.controller;

import db.backend.clinicamvc.Dto.request.TurnoRequestDto;
import db.backend.clinicamvc.Dto.response.TurnoResponseDto;
import db.backend.clinicamvc.entity.Turno;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.ITurnoService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoController.class);
    private ITurnoService  turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> registrarTurno(@RequestBody TurnoRequestDto turno) throws BadRequestException {
        LOGGER.info("Registrando nuevo turno: {}", turno);
        TurnoResponseDto turnoARetornar = turnoService.registrar(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoARetornar);

    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        LOGGER.info("Buscando todos los turnos");
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorId(@PathVariable Integer id){
        LOGGER.info("Buscando turno por ID: {}", id);
        TurnoResponseDto turnoResponseDto = turnoService.buscarPorId(id);
        if(turnoResponseDto != null){
            return ResponseEntity.ok(turnoResponseDto);
        }else{
            LOGGER.warn("Turno no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, TurnoRequestDto turno) throws BadRequestException {
        LOGGER.info("Actualizando turno con ID {}: {}", id, turno);
        turnoService.actualizarTurno(id, turno);
        return ResponseEntity.ok("{\"message\": \"turno actualizado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Integer id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando turno con ID: {}", id);
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"message\": \"turno eliminado\"}");
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarEntreFechas(@RequestParam String inicio, @RequestParam String fin){
        LOGGER.info("Buscando turnos entre fechas: {} y {}", inicio, fin);
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);
        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio, fechaFinal));
    }

    @GetMapping("/turnos-post")
    public ResponseEntity<List<TurnoResponseDto>> listarTurnosDesdeFechaActual() {
        LOGGER.info("Listando turnos desde la fecha actual");
        return ResponseEntity.ok(turnoService.listarTurnosDesdeFechaActual());
    }

}
