package db.backend.clinicamvc.controller;

import db.backend.clinicamvc.Dto.request.TurnoRequestDto;
import db.backend.clinicamvc.Dto.response.TurnoResponseDto;
import db.backend.clinicamvc.entity.Turno;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.ITurnoService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private ITurnoService  turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> registrarTurno(@RequestBody TurnoRequestDto turno) throws BadRequestException {
        TurnoResponseDto turnoARetornar = turnoService.registrar(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoARetornar);

    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorId(@PathVariable Integer id){
        TurnoResponseDto turnoResponseDto = turnoService.buscarPorId(id);
        if(turnoResponseDto != null){
            return ResponseEntity.ok(turnoResponseDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, TurnoRequestDto turno) throws BadRequestException {
         turnoService.actualizarTurno(id, turno);
        return ResponseEntity.ok("{\"message\": \"turno actualizado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Integer id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"message\": \"turno eliminado\"}");
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarEntreFechas(@RequestParam String inicio, @RequestParam String fin){
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);
        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio, fechaFinal));
    }

    @GetMapping("/turnos-post")
    public ResponseEntity<List<TurnoResponseDto>> listarTurnosDesdeFechaActual() {
        return ResponseEntity.ok(turnoService.listarTurnosDesdeFechaActual());
    }

}
