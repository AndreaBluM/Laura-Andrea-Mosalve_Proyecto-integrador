package db.backend.clinicamvc.service;

import db.backend.clinicamvc.Dto.request.TurnoRequestDto;
import db.backend.clinicamvc.Dto.response.TurnoResponseDto;
import db.backend.clinicamvc.entity.Turno;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {

    TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) throws BadRequestException;

    TurnoResponseDto buscarPorId(Integer id);

    List<TurnoResponseDto> buscarTodos();

    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws BadRequestException;

    void eliminarTurno(Integer id) throws ResourceNotFoundException;

    List<TurnoResponseDto> buscarTurnoEntreFechas( LocalDate startDate, LocalDate endDate);

    List<TurnoResponseDto> listarTurnosDesdeFechaActual();

}
