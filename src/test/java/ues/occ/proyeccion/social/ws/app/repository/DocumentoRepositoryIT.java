package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.dao.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Profile("test")
class DocumentoRepositoryIT {

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

        var documento1 = new Documento("Doc 1", "Test doc 1", LocalDateTime.now());
        var documento2 = new Documento("Doc 2", "Test doc 2", LocalDateTime.now());

        var unsedDocumento = new Documento("unsedDocumento", "Test unsedDocumento", LocalDateTime.now());

        documentoRepository.saveAll(List.of(documento1, documento2, unsedDocumento));

        var requerimiento1 = new Requerimiento(1, true, 2, documento1);
        var requerimiento2 = new Requerimiento(2, true, 3, documento2);

        requerimientoRepository.saveAll(List.of(requerimiento1, requerimiento2));

        var estudiante1 = new Estudiante(carnet, 300, false);
        var estudiante2 = new Estudiante("ab12345", 300, false);

        estudiante1.addRequerimiento(requerimiento1, false);
        estudiante1.addRequerimiento(requerimiento2, true);

        estudiante2.addRequerimiento(requerimiento1, false);
        estudiante2.addRequerimiento(requerimiento2, true);

        estudianteRepository.saveAll(List.of(estudiante1, estudiante2));

        var proyecto = new Proyecto(1, projectName, 250, true, LocalDateTime.now());
        proyecto.setStatus(status);
        proyecto.registerStudent(estudiante1);
        proyecto.registerStudent(estudiante2);
        proyectoRepository.save(proyecto);


        var result = documentoRepository.findProjectRelatedDocuments(carnet, "Anything");
        assertEquals(result.size(), 2);

        var requerimientos1 = result.get(0).getRequerimientos();
        var resultRequerimiento1 = getFirstItem(requerimientos1);
        var requerimientoEstudiante1 = resultRequerimiento1.getEstadoRequerimientoEstudiantes();

        var resultRequerimientoEstudianteOptional1 = test(requerimientoEstudiante1, carnet);
        assertTrue(resultRequerimientoEstudianteOptional1.isPresent());
        var resultRequerimientoEstudiante1 = resultRequerimientoEstudianteOptional1.get();

        assertEquals(1, requerimientos1.size());
        assertEquals(resultRequerimiento1.getcantidadCopias(), 2);
        assertFalse(resultRequerimientoEstudiante1.isAprobado());

        var requerimientos2 = result.get(1).getRequerimientos();
        var resultRequerimiento2 = getFirstItem(requerimientos2);
        var requerimientoEstudiante2 = resultRequerimiento2.getEstadoRequerimientoEstudiantes();

        var resultRequerimientoEstudianteOptional2 = test(requerimientoEstudiante2, carnet);
        assertTrue(resultRequerimientoEstudianteOptional2.isPresent());
        var resultRequerimientoEstudiante2 = resultRequerimientoEstudianteOptional2.get();

        assertEquals(1, requerimientos2.size());
        assertEquals(resultRequerimiento2.getcantidadCopias(), 3);
        assertTrue(resultRequerimientoEstudiante2.isAprobado());


    }

    private <T> T getFirstItem(Set<T> set){
        var iter = set.iterator();
        return iter.next();
    }
    private Optional<EstadoRequerimientoEstudiante> test(Set<EstadoRequerimientoEstudiante> set, String carnet){
        return set.stream().filter(obj -> obj.getEstudiante().getCarnet().equals(carnet)).findFirst();
    }
}