package db.backend.clinicamvc.service;

import db.backend.clinicamvc.Dto.request.TurnoRequestDto;
import db.backend.clinicamvc.Dto.response.TurnoResponseDto;
import db.backend.clinicamvc.entity.Turno;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {

    TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto);

    TurnoResponseDto buscarPorId(Integer id);

    List<TurnoResponseDto> buscarTodos();

    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto);

    void eliminarTurno(Integer id);

    List<TurnoResponseDto> buscarTurnoEntreFechas( LocalDate startDate, LocalDate endDate);

    List<TurnoResponseDto> listarTurnosDesdeFechaActual();

}
