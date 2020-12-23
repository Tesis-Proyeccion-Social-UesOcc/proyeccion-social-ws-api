package ues.occ.proyeccion.social.ws.app.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.dao.Status;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.RequerimientoRepository;
import ues.occ.proyeccion.social.ws.app.repository.StatusRepository;
import ues.occ.proyeccion.social.ws.app.service.EstadoRequerimientoEstudianteService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// @TestPropertySource(locations="classpath:application-test.properties")
public class SpringDataIT {
    @Autowired
    EstadoRequerimientoEstudianteRepository repository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    EstadoRequerimientoEstudianteService service;

    @Autowired
    RequerimientoRepository requerimientoRepository;

    @Autowired
    EstudianteRepository estudianteRepository;

    @Test
    public void test() throws Exception {
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet("ZW15002");
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setCarnet("ZW15003");
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setId(1);
        var estudianteResult = estudianteRepository.save(estudiante);
        var estudiante2R = estudianteRepository.save(estudiante2);
        var requerimientoResult = requerimientoRepository.save(requerimiento);
        EstadoRequerimientoEstudiante toTest = new EstadoRequerimientoEstudiante(estudianteResult, requerimientoResult, true, true, null, null);
        repository.save(toTest);
        EstadoRequerimientoEstudiante toTest2= new EstadoRequerimientoEstudiante(estudiante2R, requerimientoResult, true, true, null, null);
        EstadoRequerimientoEstudiante result = repository.save(toTest2);
        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void test2(){
        Status s = new Status();
        s.setDescripcion("Prueba");
        s.setStatus("Prueba");
        s.setId(1);
        statusRepository.save(s);
        Assertions.assertEquals(1, statusRepository.count());
    }

    @Test
    public void last(){
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet("ZW15002");
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setId(1);
        System.out.println("**********GUARDANDO*********");
        var estudianteResult = estudianteRepository.save(estudiante);
        var requerimientoResult = requerimientoRepository.save(requerimiento);
        System.out.println("**********GUARDANDO*********");
        service.save("ZW15002", 1);

        Assertions.assertEquals(1, repository.count());
    }
}
