package db.backend.clinicamvc.repository;

import db.backend.clinicamvc.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

    @Query("Select o from Paciente o where o.dni =?1")
    List<Paciente> buscarPorDni(String dni);
}
