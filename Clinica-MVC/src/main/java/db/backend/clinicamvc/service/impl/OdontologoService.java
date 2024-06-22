package db.backend.clinicamvc.service.impl;

import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.repository.IOdontologoRepository;
import db.backend.clinicamvc.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService  implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo agregarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarUnOdontologo(int id) {
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> buscarTodosOdontologos() {
        return odontologoRepository.findAll();
    }

    @Override
    public void  modificarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = buscarUnOdontologo(id);
        if(odontologoOptional.isPresent())
            odontologoRepository.deleteById(id);
        else throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        return odontologoRepository.findByNombreLike(nombre);
    }
}
