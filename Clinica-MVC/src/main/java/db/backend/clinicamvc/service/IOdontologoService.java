package db.backend.clinicamvc.service;

import db.backend.clinicamvc.entity.Odontologo;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo agregarOdontologo(Odontologo odontologo);
    Optional<Odontologo> buscarUnOdontologo(int id);
    List<Odontologo> buscarTodosOdontologos();

    void modificarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Integer id);

    List<Odontologo> buscarPorApellido(String apellido);
    List<Odontologo> buscarPorNombre(String nombre);

}
