package db.backend.clinicamvc.repository;

import db.backend.clinicamvc.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    @Query("Select o from Odontologo o where o.apellido =?1")
    List<Odontologo> buscarPorApellido(String apellido);

    @Query("Select o from Odontologo o where LOWER(o.nombre) LIKE LOWER(CONCAT('%',:nombre,'%'))")
    List<Odontologo> findByNombreLike(String nombre);
}
