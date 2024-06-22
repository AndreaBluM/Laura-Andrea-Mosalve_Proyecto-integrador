package db.backend.clinicamvc.service.impl;

import db.backend.clinicamvc.Dto.request.TurnoRequestDto;
import db.backend.clinicamvc.Dto.response.OdontologoResponseDto;
import db.backend.clinicamvc.Dto.response.PacienteResponseDto;
import db.backend.clinicamvc.Dto.response.TurnoResponseDto;
import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.entity.Turno;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.repository.IOdontologoRepository;
import db.backend.clinicamvc.repository.IPacienteRepository;
import db.backend.clinicamvc.repository.ITurnoRepository;
import db.backend.clinicamvc.service.ITurnoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

     private ITurnoRepository turnoRepository;
     private IPacienteRepository pacienteRepository;
     private IOdontologoRepository odontologoRepository;
     private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) throws BadRequestException {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;

        if(paciente.isPresent() && odontologo.isPresent()){
            turnoARegistrar.setOdontologo(odontologo.get());
            turnoARegistrar.setPaciente(paciente.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);
            turnoADevolver = mapToResponseDtop(turnoGuardado);
        }else{
            throw new BadRequestException("{\"message\": \"turno no encontrado\"}");
        }
        return turnoADevolver;
    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if(turnoOptional.isPresent()){
            Turno turnoEncontrado = turnoOptional.get();
            TurnoResponseDto turnoADevolver = mapToResponseDtop(turnoEncontrado);
            return turnoADevolver;
        }
        return null;
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for(Turno turno: turnos){
            turnoAuxiliar = mapToResponseDtop(turno);
            turnosADevolver.add(turnoAuxiliar);
        }
        return turnosADevolver;
    }



    @Override
    public void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws BadRequestException {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Optional<Turno> turno1 = turnoRepository.findById(id);
        Turno turnoAModificar = new Turno();
        if(paciente.isPresent() && odontologo.isPresent() && turno1.isPresent()){
            turnoAModificar.setId(id);
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoRepository.save(turnoAModificar);
        } else {
            throw new BadRequestException("{\"message\": \"turno no encontrado\"}");
        }

    }

    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        TurnoResponseDto turnoResponseDto = buscarPorId(id);
        if(turnoResponseDto !=null)
            turnoRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnoEntreFechas(startDate,endDate);
        List<TurnoResponseDto> listadoARetornar = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno: listadoTurnos){
            turnoAuxiliar = mapToResponseDtop(turno);
            listadoARetornar.add(turnoAuxiliar);
        }
        return listadoARetornar;
    }

    @Override
    public List<TurnoResponseDto> listarTurnosDesdeFechaActual() {
        LocalDate today = LocalDate.now();
        List<Turno> listadoTurnos = turnoRepository.listarTurnosDesdeFechaActual(today);
        List<TurnoResponseDto> listaARetornar = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno : listadoTurnos) {
            turnoAuxiliar = mapToResponseDtop(turno);
            listaARetornar.add(turnoAuxiliar);
        }
        return listaARetornar;
    }


    private TurnoResponseDto mapToResponseDtop(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }
}
