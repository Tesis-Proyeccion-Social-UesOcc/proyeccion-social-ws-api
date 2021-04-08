package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void findByProyectoEstudiante_Estudiante_CarnetIgnoreCaseAndProyectoEstudiante_Proyecto_NombreIgnoreCase() {

        String carnet = "zh15002", projectName = "Test project", uri = "www.somegcpurl.com";
        // when
        var status = new Status(1, "completado", "some description");
        statusRepository.save(status);
        var estudiante = new Estudiante(carnet, 300, false);
        estudianteRepository.save(estudiante);

        var proyecto = new Proyecto(1, projectName, 250, true, LocalDateTime.now());
        var proyectoEstudiante = proyecto.registerStudent(estudiante);

        var cert1 = certificadoRepository.findAll();


        proyectoRepository.save(proyecto);
        var saved = proyectoEstudianteRepository.findById(1);
        assertTrue(saved.isPresent());
        var actualProyectoEstudiante = saved.get();
        var cert2 = certificadoRepository.findAll();

        var certificado = new Certificado(1, uri, LocalDateTime.now(), actualProyectoEstudiante);
        var bar = certificadoRepository.save(certificado);

        var cert3 = certificadoRepository.findAll();

        // then
        var c = certificadoRepository.findAll();
        var result = certificadoRepository
                .findByProyectoEstudiante_Estudiante_CarnetIgnoreCaseAndProyectoEstudiante_Proyecto_NombreIgnoreCase(carnet, "test project"); //char sequence to test case insensitive query
        assertTrue(result.isPresent());
        var certificadoResult = result.get();

        assertEquals(certificadoResult.getProyectoEstudiante().getEstudiante().getCarnet(), carnet);
        assertEquals(certificadoResult.getProyectoEstudiante().getProyecto().getNombre(), projectName);
        assertEquals(certificadoResult.getUri(), uri);

    }
}