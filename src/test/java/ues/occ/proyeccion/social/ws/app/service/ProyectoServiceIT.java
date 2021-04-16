package ues.occ.proyeccion.social.ws.app.service;

import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.controller.ProyectoEstudianteController;
import ues.occ.proyeccion.social.ws.app.dao.*;
import ues.occ.proyeccion.social.ws.app.mappers.ProyectoMapper;
import ues.occ.proyeccion.social.ws.app.model.PendingProjectDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.*;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Profile("test")
public class ProyectoServiceIT {

    // following mockbeans are to avoid exceptions related to gcp Storage
    @MockBean CertificadoService service;
    @MockBean Storage storage;
    @MockBean ProyectoEstudianteController proyectoEstudianteController;

    @Autowired DocumentoRepository documentoRepository;
    @Autowired  RequerimientoRepository requerimientoRepository;
    @Autowired StatusRepository statusRepository;
    @Autowired  EstudianteRepository estudianteRepository;
    @Autowired ProyectoRepository proyectoRepository;
    @Autowired DepartamentoRepository departamentoRepository;
    @Autowired PersonalRepository personalRepository;
    @Autowired TipoPersonalRepository tipoPersonalRepository;
    @Autowired EntityManager entityManager;
    @Autowired ProyectoMapper proyectoMapper;


    @Test
    void serviceSave() {
        var service = new ProyectoServiceImpl(proyectoRepository, documentoRepository, proyectoMapper,
                estudianteRepository, requerimientoRepository, entityManager);
        var carnet = "zh15002";
        var status = new Status(1, "Pendiente", "desc");
        var dep = new Departamento(1, "Ing");
        var tipo = new TipoPersonal(1, "Docente", "any");
        var personal = new Personal(1, "chepe", "lastname", dep, tipo);
        personal.setEmail("some.email@gmail.com");
        var doc = new Documento("doc1", "desc", LocalDateTime.now());
        var req1 = new Requerimiento(1, true, 2, doc);

        statusRepository.save(status);
        departamentoRepository.save(dep);
        tipoPersonalRepository.save(tipo);
        personalRepository.save(personal);
        documentoRepository.save(doc);
        requerimientoRepository.save(req1);
        estudianteRepository.save(new Estudiante(carnet, 100, false));

        assertEquals(0, proyectoRepository.count());

        var proyecto = new ProyectoCreationDTO("proyecto", 250, true, 1, List.of(carnet));

        service.save(proyecto);
        assertEquals(1, proyectoRepository.count());
        var c = documentoRepository.findProjectRelatedDocuments("zh15002", "proyecto");

        var result = proyectoRepository.findById(1);
        assertTrue(result.isPresent());

        var project = result.get();

        assertEquals(project.getNombre(), proyecto.getNombre());
        assertEquals(project.getDuracion(), 250);
        assertTrue(project.isInterno());

        var requirement = (PendingProjectDTO) service.getRequirementsData(carnet).get(0);
        var docs = requirement.getDocumentos().iterator().next();
        assertEquals(requirement.getNombre(), proyecto.getNombre());
        assertEquals(requirement.getDuracion(), 250);
        assertTrue(requirement.isInterno());
        assertEquals(requirement.getPersonal(), personal.getNombre());
        assertEquals(requirement.getStatus(), status.getStatus());
        assertEquals(docs.getNombre(), doc.getNombre());
    }
}
