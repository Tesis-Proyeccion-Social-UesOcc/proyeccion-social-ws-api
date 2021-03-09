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
        estudianteRepository.save(estudiante);

        var proyecto = new Proyecto(1, projectName, 250, true, LocalDateTime.now());
        proyecto.registerStudent(estudiante);

        proyectoRepository.save(proyecto);

        var certificado = new Certificado(1, uri, LocalDateTime.now(), proyecto);
        certificadoRepository.save(certificado);

        // then

        var result = certificadoRepository
                .findByProyecto_ProyectoEstudianteSet_Estudiante_CarnetAndProyecto_NombreContainingIgnoreCase(carnet, "test proj"); //char sequence to test case insensitive query
        assertTrue(result.isPresent());
        var certificadoResult = result.get();

        assertEquals(data(certificadoResult.getProyecto()).getCarnet(), carnet);
        assertEquals(certificadoResult.getProyecto().getNombre(), projectName);
        assertEquals(certificadoResult.getUri(), uri);

    }
    private Estudiante data(Proyecto proyecto){
        var data = proyecto.getProyectoEstudianteSet();
        var array = data.toArray(new ProyectoEstudiante[0]);
        return array[0].getEstudiante();
    }
}