package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.dao.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// activate the test profile to test it ===> spring.profiles.active=test
@DataJpaTest
@Profile("test")
class CertificadoRepositoryIT {


    @Autowired EstudianteRepository estudianteRepository;
    @Autowired ProyectoRepository proyectoRepository;
    @Autowired ProyectoEstudianteRepository proyectoEstudianteRepository;
    @Autowired CertificadoRepository certificadoRepository;
    @Autowired StatusRepository statusRepository;

    @Test
    void findByProyectoEstudiante_Estudiante_CarnetAndProyectoEstudiante_Proyecto_NombreContainingIgnoreCase() {

        String carnet = "zh15002", projectName = "Test project", uri = "www.somegcpurl.com";
        // when
        var status = new Status(1, "completado", "some description");
        statusRepository.save(status);
        var estudiante = new Estudiante(carnet, 300, false);
        var proyecto = new Proyecto(1, projectName, true, LocalDateTime.now());
        proyecto.setDuracion(250);
        estudianteRepository.save(estudiante);
        proyectoRepository.save(proyecto);
        var proyectoEstudiante = new ProyectoEstudiante(estudiante, proyecto);
        proyectoEstudianteRepository.save(proyectoEstudiante);
        var certificado = new Certificado(1, uri, LocalDateTime.now(), proyectoEstudiante);
        certificadoRepository.save(certificado);

        // then
        var result = certificadoRepository
                .findByProyectoEstudiante_Estudiante_CarnetAndProyectoEstudiante_Proyecto_NombreContainingIgnoreCase(carnet, "test proj"); //char sequence to test case insensitive query
        assertTrue(result.isPresent());

        var certificadoResult = result.get();
        assertEquals(certificadoResult.getProyectoEstudiante().getEstudiante().getCarnet(), carnet);
        assertEquals(certificadoResult.getProyectoEstudiante().getProyecto().getNombre(), projectName);
        assertEquals(certificadoResult.getUri(), uri);

    }
}