package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.Status;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Profile("test")
class ProyectoRepositoryIT {

    @Autowired DocumentoRepository documentoRepository;
    @Autowired RequerimientoRepository requerimientoRepository;
    @Autowired StatusRepository statusRepository;
    @Autowired EstudianteRepository estudianteRepository;
    @Autowired ProyectoRepository proyectoRepository;
    @Autowired DepartamentoRepository departamentoRepository;
    @Autowired PersonalRepository personalRepository;
    @Autowired TipoPersonalRepository tipoPersonalRepository;
    @Autowired EntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase() {
        var student = new Estudiante("zh15002", 200, true);
        estudianteRepository.save(student);
        var status = new Status(1, "Pendiente", "desc");
        statusRepository.save(status);
        var project = new Proyecto(1, "Test project", 200, true, LocalDateTime.now());
        project.setStatus(status);
        project.registerStudent(student);
        proyectoRepository.save(project);

        var page = PageRequest.of(0, 5);
        var result = proyectoRepository.findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(1, "zh15002", page);

        assertEquals(result.getContent().size(), 1);
        assertEquals(result.getContent().get(0).getNombre(), "Test project");
    }
}