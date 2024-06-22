package db.backend.clinicamvc.service.impl;

import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.repository.IPacienteRepository;
import db.backend.clinicamvc.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Integer id){
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos(){
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = buscarPorId(id);
        if(pacienteOptional.isPresent()){
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }

    }

    @Override
    public List<Paciente> buscarPorDni(String dni) {
        return pacienteRepository.buscarPorDni(dni);
    }
}
