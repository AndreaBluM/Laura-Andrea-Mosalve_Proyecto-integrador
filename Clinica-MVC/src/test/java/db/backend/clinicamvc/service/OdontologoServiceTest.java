package db.backend.clinicamvc.service;

import db.backend.clinicamvc.entity.Domicilio;
import db.backend.clinicamvc.entity.Odontologo;
import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
import db.backend.clinicamvc.service.impl.OdontologoService;
import db.backend.clinicamvc.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNroMatricula("Carlos");
        odontologo.setNombre("Mendez");
        odontologo.setApellido("070707");

    }

    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado(){
        Odontologo odntologoDesdeLaBD = odontologoService.agregarOdontologo(odontologo);
        assertNotNull(odntologoDesdeLaBD);
    }

    @Test
    @DisplayName("Testear busqueda odontologo por id")
    void testOdontologoPorId() throws ResourceNotFoundException {
        Integer id = 1;
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarUnOdontologo(id);
        Odontologo odontologo1 = odontologoEncontrado.get();
        assertEquals(id, odontologo1.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void testBusquedaTodos() {
        odontologoService.agregarOdontologo(odontologo);
        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();
        assertTrue( odontologos.size()!=0);

    }
}
