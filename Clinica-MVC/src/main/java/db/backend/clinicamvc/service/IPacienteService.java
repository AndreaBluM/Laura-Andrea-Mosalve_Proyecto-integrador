package db.backend.clinicamvc.service;

import db.backend.clinicamvc.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

        Paciente registrarPaciente(Paciente paciente);

        Optional<Paciente> buscarPorId(Integer id);

        List<Paciente> buscarTodos();

        void actualizarPaciente(Paciente paciente);
        void eliminarPaciente(Integer id);

        List<Paciente> buscarPorDni(String dni);

}
