package db.backend.clinicamvc.service;

import db.backend.clinicamvc.Dto.request.TurnoRequestDto;
import db.backend.clinicamvc.Dto.response.TurnoResponseDto;
import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.entity.Turno;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.impl.OdontologoService;
import db.backend.clinicamvc.service.impl.PacienteService;
import db.backend.clinicamvc.service.impl.TurnoService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;

    private TurnoRequestDto turnoRequestDto;
    private Odontologo odontologo;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        odontologo = new Odontologo();
        odontologo.setNroMatricula("070707");
        odontologo.setNombre("Carlos");
        odontologo.setApellido("Mendez");
        odontologo = odontologoService.agregarOdontologo(odontologo);

        paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setDni("12345678");
        paciente = pacienteService.registrarPaciente(paciente);

        turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setOdontologo_id(turnoRequestDto.getOdontologo_id());
        turnoRequestDto.setPaciente_id(turnoRequestDto.getPaciente_id());
        turnoRequestDto.setFecha(String.valueOf(LocalDate.from(LocalDateTime.now())));
    }

}

