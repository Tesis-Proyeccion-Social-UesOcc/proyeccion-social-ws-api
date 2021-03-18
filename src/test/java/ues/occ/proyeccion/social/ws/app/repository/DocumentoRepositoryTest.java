package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.dao.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Profile("test")
class DocumentoRepositoryTest {

    @Autowired DocumentoRepository documentoRepository;
    @Autowired RequerimientoRepository requerimientoRepository;
    @Autowired EstadoRequerimientoEstudianteRepository requerimientoEstudianteRepository;
    @Autowired EstudianteRepository estudianteRepository;
    @Autowired ProyectoRepository proyectoRepository;
    @Autowired StatusRepository statusRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findProjectRelatedDocuments() {
        String projectName = "anything", carnet = "zh15002";
        var status = new Status(1, "Pendiente", "pendiente");
        statusRepository.save(status);

        var documento1 = new Documento("Doc 1", "Test doc 1", "www.google.com", LocalDateTime.now());
        var documento2 = new Documento("Doc 2", "Test doc 2", "www.google.com", LocalDateTime.now());

        var unsedDocumento = new Documento("unsedDocumento", "Test unsedDocumento", "www.google.com", LocalDateTime.now());

        documentoRepository.saveAll(List.of(documento1, documento2, unsedDocumento));

        var requerimiento1 = new Requerimiento(1, true, 2, documento1);
        var requerimiento2 = new Requerimiento(2, true, 3, documento2);

        requerimientoRepository.saveAll(List.of(requerimiento1, requerimiento2));

        var toDebug = requerimientoRepository.findAll();
        var toDebug3 = documentoRepository.findAll();

        var estudiante = new Estudiante(carnet, 300, false);
        estudiante.addRequerimiento(requerimiento1, false);
        estudiante.addRequerimiento(requerimiento2, true);
        estudianteRepository.save(estudiante);

        var toDebug2 = estudianteRepository.findAll();

        var proyecto = new Proyecto(1, projectName, 250, true, LocalDateTime.now());
        proyecto.setStatus(status);
        proyecto.registerStudent(estudiante);
        proyectoRepository.save(proyecto);


        var result = documentoRepository.findProjectRelatedDocuments(1, "zH15002", "Anything");
        assertEquals(result.size(), 2);

        var requerimientos1 = result.get(0).getRequerimientos();
        var resultRequerimiento1 = getFirstItem(requerimientos1);
        var requerimientoEstudiante1 = resultRequerimiento1.getEstadoRequerimientoEstudiantes();
        var resultRequerimientoEstudiante1 = getFirstItem(requerimientoEstudiante1);
        assertEquals(1, requerimientos1.size());
        assertEquals(resultRequerimiento1.getcantidadCopias(), 2);
        assertEquals(1, requerimientoEstudiante1.size());
        assertFalse(resultRequerimientoEstudiante1.isAprobado());

        var requerimientos2 = result.get(1).getRequerimientos();
        var resultRequerimiento2 = getFirstItem(requerimientos2);
        var requerimientoEstudiante2 = resultRequerimiento2.getEstadoRequerimientoEstudiantes();
        var resultRequerimientoEstudiante2 = getFirstItem(requerimientoEstudiante2);
        assertEquals(1, requerimientos2.size());
        assertEquals(resultRequerimiento2.getcantidadCopias(), 3);
        assertEquals(1, requerimientoEstudiante2.size());
        assertTrue(resultRequerimientoEstudiante2.isAprobado());


    }

    private <T> T getFirstItem(Set<T> set){
        var iter = set.iterator();
        return iter.next();
    }
}