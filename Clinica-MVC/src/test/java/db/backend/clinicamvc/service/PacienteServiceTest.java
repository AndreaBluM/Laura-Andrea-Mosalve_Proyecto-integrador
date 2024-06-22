package db.backend.clinicamvc.service;

import db.backend.clinicamvc.entity.Domicilio;
import db.backend.clinicamvc.entity.Paciente;
import db.backend.clinicamvc.exception.ResourceNotFoundException;
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
class PacienteServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);
    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente;

    @BeforeEach
        void setUp(){
        paciente = new Paciente();
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024,02,12));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("San Pedro");
        domicilio.setProvincia("Jujuy");
        paciente.setDomicilio(domicilio);

    }

    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado(){
        Paciente pacienteDesdeLaBD = pacienteService.registrarPaciente(paciente);
        assertNotNull(pacienteDesdeLaBD);
    }

    @Test
    @DisplayName("Testear busqueda paciente por id")
    void testPacientePorId() throws ResourceNotFoundException {
        Integer id = 1;
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);
        Paciente paciente1 = pacienteEncontrado.get();
        assertEquals(id, paciente1.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los pacientes")
    void testBusquedaTodos() {
        pacienteService.registrarPaciente(paciente);
        List<Paciente> pacientes = pacienteService.buscarTodos();
        assertTrue( pacientes.size()!=0);

    }


}