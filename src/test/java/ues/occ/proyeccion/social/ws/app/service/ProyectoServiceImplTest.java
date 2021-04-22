package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ues.occ.proyeccion.social.ws.app.dao.*;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.ProyectoMapper;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.PendingProjectDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.DocumentoRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;
import ues.occ.proyeccion.social.ws.app.repository.RequerimientoRepository;
import ues.occ.proyeccion.social.ws.app.repository.projections.RequerimientoIdView;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProyectoServiceImplTest {

    private static final int PAGE = 5;
    private static final int SIZE = 10;

    @Autowired private ProyectoMapper proyectoMapper;

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private RequerimientoRepository requerimientoRepository;

    @Mock
    private DocumentoRepository documentoRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    EntityManager entityManager;

    private ProyectoServiceImpl proyectoService;

    Proyecto proyecto1;
    Proyecto proyecto2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proyectoService = new ProyectoServiceImpl(proyectoRepository, documentoRepository, proyectoMapper, estudianteRepository, requerimientoRepository, entityManager);

        proyecto1 = new Proyecto();
        var status = new Status(2, "statusName", "desc");
        proyecto1.setStatus(status);
        proyecto1.setNombre("proyecto1");
        proyecto1.setInterno(false);
        PersonalExterno personalExterno = new PersonalExterno();
        personalExterno.setNombre("text");

        var estudiante1 = new Estudiante();
        var estudiante2 = new Estudiante();

        estudiante1.setCarnet("ZH15002");
        estudiante1.setHorasProgreso(500);
        estudiante1.setServicioCompleto(true);
        estudiante2.setCarnet("AB15002");
        estudiante2.setHorasProgreso(200);
        estudiante2.setServicioCompleto(false);

        var proyectoEstudiante1 = new ProyectoEstudiante();
        var proyectoEstudiante2 = new ProyectoEstudiante();
        proyectoEstudiante1.setEstudiante(estudiante1);
        proyectoEstudiante2.setEstudiante(estudiante2);
        proyectoEstudiante1.setProyecto(proyecto1);
        proyectoEstudiante2.setProyecto(proyecto2);
        proyectoEstudiante1.setId(1);
        proyectoEstudiante2.setId(2);
        var proyectoEstudiantes = new HashSet<ProyectoEstudiante>(Set.of(proyectoEstudiante1, proyectoEstudiante2));
        proyecto1.setEncargadoExterno(personalExterno);
        proyecto1.setTutor(new Personal());
        proyecto1.setProyectoEstudianteSet(proyectoEstudiantes);
        proyecto2 = new Proyecto();
        proyecto2.setNombre("proyecto2");
        proyecto2.setInterno(true);
        proyecto2.setTutor(new Personal());
        proyecto2.setProyectoEstudianteSet(proyectoEstudiantes);
    }

    @Test
    void testFindById() {
        var proyecto = new Proyecto();
        proyecto.setId(101);
        proyecto.setDuracion(500);
        proyecto.setTutor(new Personal());
        proyecto.setInterno(true);

        Optional<Proyecto> proyecto_ = Optional.of(proyecto);
        Mockito.when(this.proyectoRepository.findById(Mockito.anyInt()))
                .thenReturn(proyecto_);

        ProyectoCreationDTO.ProyectoDTO resultObject = this.proyectoService.findById(1);

        assertNotNull(resultObject);
        assertEquals(resultObject.getId(), 101);
        assertEquals(resultObject.getDuracion(), 500);
    }

    @Test
    void testFindNonexistent() {
        Mockito.when(this.proyectoRepository.findById(10))
                .thenReturn(Optional.of(new Proyecto()));
        assertThrows(ResourceNotFoundException.class, () -> this.proyectoService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.when((this.proyectoRepository.findAll(Mockito.any(Pageable.class)))).thenReturn(page);
        var result = this.proyectoService.findAll(5, 10);
        Mockito.verify(
                this.proyectoRepository,
                Mockito.times(1)
        ).findAll(captor.capture());
        Pageable captured = captor.getValue();

        var estudianteDTO1 = new EstudianteDTO(1, "ZH15002", 500, true);
        var estudianteDTO2 = new EstudianteDTO(2, "AB15002", 200, false);
        var expected = Set.of(estudianteDTO1, estudianteDTO2);

        assertEquals(5, captured.getPageNumber());
        assertEquals(10, captured.getPageSize());
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(expected, result.getContent().get(0).getEstudiantes());
    }

    @Test
    void testFindAllByStatus(){
        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.proyectoRepository.findAllByStatus_Id(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findAllByStatus(5, 10, 1);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatus_Id(statusCaptor.capture(), pageableArgumentCaptor.capture());

        assertNotNull(result);
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        // prueba con get content
        assertEquals(2, result.getContent().size());
        assertFalse(result.getContent().get(0).isInterno());
        assertEquals(result.getContent().get(0).getPersonal(), "text");
        assertTrue(result.getContent().get(1).isInterno());
        assertEquals(1, statusCaptor.getValue());

    }

    @Test
    void testFindAllPending(){
        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());

        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.proyectoRepository.findAllByStatus_Id(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findAllPending(5, 10);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatus_Id(statusCaptor.capture(), pageableArgumentCaptor.capture());

        assertNotNull(result);
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        assertEquals(2, result.getContent().size());
        assertFalse(result.getContent().get(0).isInterno());
        assertEquals(result.getContent().get(0).getPersonal(), "text");
        assertTrue(result.getContent().get(1).isInterno());
        assertEquals(1, statusCaptor.getValue());

    }

    @Test
    void testFindByCarnetAndProjectNameWhenIsNoPending(){
        var toReturn = Optional.of(proyecto1);

        Mockito.when(this.proyectoRepository.findByProyectoEstudianteSet_Estudiante_CarnetIgnoreCaseAndNombreIgnoreCase(Mockito.anyString(), Mockito.anyString())).thenReturn(toReturn);

        var result = (ProyectoCreationDTO.ProyectoDTO) this.proyectoService.findByCarnetAndProjectName("ab12345", "test");

        Mockito.verify(this.documentoRepository, Mockito.never()).findProjectRelatedDocuments(Mockito.anyString(), Mockito.anyString());
        assertNotNull(result);
        assertEquals(result.getDuracion(), proyecto1.getDuracion());
        assertEquals(result.getId(), proyecto1.getId());
        assertEquals(result.getNombre(), proyecto1.getNombre());
    }

    @Test
    void testFindByCarnetAndProjectNameWhenIsPending(){
        proyecto1.getStatus().setId(1);
        var toReturn = Optional.of(proyecto1);

        var document = new Documento("doc", "my doc", LocalDateTime.now());
        var requerimiento = new Requerimiento(1, true, 2, document);
        var student1 = new Estudiante("ab12345", 200, false);
        var student2 = new Estudiante("zh15002", 200, false);
        var projectStudent1 = proyecto1.registerStudent(student1);
        projectStudent1.setId(1);
        requerimiento.addEstadoRequerimiento(projectStudent1, false);

        var docs = List.of(document);

        Mockito.when(this.proyectoRepository.findByProyectoEstudianteSet_Estudiante_CarnetIgnoreCaseAndNombreIgnoreCase(Mockito.anyString(), Mockito.anyString())).thenReturn(toReturn);
        Mockito.when(this.documentoRepository.findProjectRelatedDocuments(Mockito.anyString(), Mockito.anyString())).thenReturn(docs);

        var result = (PendingProjectDTO) this.proyectoService.findByCarnetAndProjectName("ab12345", "test");

        assertNotNull(result);
        assertEquals(result.getDuracion(), proyecto1.getDuracion());
        assertEquals(result.getId(), proyecto1.getId());
        assertEquals(result.getNombre(), proyecto1.getNombre());
        assertEquals(result.getDocumentos().iterator().next().getNombre(), docs.get(0).getNombre());
    }

    @Test
    void testFindProyectosByEstudiante(){

        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Integer> statusArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.proyectoRepository.findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(
                Mockito.anyInt(), Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findProyectosByEstudiante(5, 10, "zh", 3);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(
                        statusArgumentCaptor.capture(), carnetArgumentCaptor.capture(), pageableArgumentCaptor.capture()
                );

        assertNotNull(result);
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        // prueba con content
        assertEquals(2, result.getContent().size());
        assertFalse(result.getContent().get(0).isInterno());
        assertEquals(result.getContent().get(0).getPersonal(), "text");
        assertTrue(result.getContent().get(1).isInterno());
        assertEquals("zh", carnetArgumentCaptor.getValue());
        assertEquals(3, statusArgumentCaptor.getValue());

    }

    @Test
    void findProyectosPendientesByEstudiante(){

        var document = new Documento("doc", "my doc", LocalDateTime.now());
        var requerimiento = new Requerimiento(1, true, 2, document);
        var student1 = new Estudiante("ab12345", 200, false);
        var student2 = new Estudiante("zh15002", 200, false);
        var projectStudent1 = proyecto1.registerStudent(student1);
        projectStudent1.setId(1);
        var projectStudent2 = proyecto2.registerStudent(student2);
        projectStudent2.setId(2);
        requerimiento.addEstadoRequerimiento(projectStudent1, false);
        requerimiento.addEstadoRequerimiento(projectStudent2, false);


        var data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());

        var docs = List.of(document);

        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Integer> statusArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.proyectoRepository.findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(
                Mockito.anyInt(), Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(page);

        Mockito.when(this.documentoRepository
                .findProjectRelatedDocuments(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(docs);

        var result = this.proyectoService.findProyectosPendientesByEstudiante(5, 10, "zh", 3);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(
                        statusArgumentCaptor.capture(), carnetArgumentCaptor.capture(), pageableArgumentCaptor.capture()
                );

        Mockito.verify(this.documentoRepository, Mockito.times(2))
                .findProjectRelatedDocuments(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

        assertNotNull(result);
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());

        assertEquals(2, result.getContent().size());
        assertFalse(result.getContent().get(0).isInterno());
        assertEquals(result.getContent().get(0).getPersonal(), "text");
        assertTrue(result.getContent().get(1).isInterno());
        assertEquals("doc", result.getContent().get(1).getDocumentos().iterator().next().getNombre());
        assertEquals("zh", carnetArgumentCaptor.getValue());
        assertEquals(3, statusArgumentCaptor.getValue());
    }

    @Test
    void testGetRequirementsData(){
        String projectName1 = "test1", projectName2 = "test2", carnet = "ab12345";
        var pendiente = new Status(1, "pendiente", "");
        var activo = new Status(2, "en proceso", "");
        var project1 = new Proyecto(1, projectName1, true, null);
        project1.setStatus(pendiente);
        project1.setTutor(new Personal(1, "tutor1", ""));
        var project2 = new Proyecto(2, projectName2, true, null);
        project2.setStatus(activo);
        project2.setTutor(new Personal(2, "tutor2", ""));
        var projects = List.of(project1, project2);


        var document = new Documento("doc", "my doc", LocalDateTime.now());
        var requerimiento = new Requerimiento(1, true, 2, document);
        var student = new Estudiante("zh15002", 200, false);
        var projectStudent = new ProyectoEstudiante(student, proyecto1, true);
        projectStudent.setId(1);
        requerimiento.addEstadoRequerimiento(projectStudent, false);
        var docs = List.of(document);

        var carnetCapture = ArgumentCaptor.forClass(String.class);
        var projectNameCapture = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.proyectoRepository.findAllByProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(Mockito.anyString())).thenReturn(projects);
        Mockito.when(this.documentoRepository.findProjectRelatedDocuments(Mockito.anyString(), Mockito.anyString())).thenReturn(docs);

        var result = this.proyectoService.getRequirementsData(carnet);

        Mockito.verify(this.documentoRepository, Mockito.times(1))
                .findProjectRelatedDocuments(carnetCapture.capture(), projectNameCapture.capture());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof PendingProjectDTO);
        assertEquals(((PendingProjectDTO) result.get(0)).getNombre(), projectName1);
        assertTrue(result.get(1) instanceof ProyectoCreationDTO.ProyectoDTO);
        assertEquals(((ProyectoCreationDTO.ProyectoDTO) result.get(1)).getNombre(), projectName2);
        assertEquals(carnet, carnetCapture.getValue());
        assertEquals(projectName1, projectNameCapture.getValue());

    }

    @Test
    void testSave(){
        String carnet = "ZH15002";
        Personal personal = new Personal();
        personal.setId(1);
        personal.setNombre("Steve");

        var status = new Status(1, "DummyStatus", "DummyDesc");

        var carnets = List.of(carnet);

        ProyectoCreationDTO proyectoCreationDTO = new ProyectoCreationDTO("Project", 150, true, 1, carnets);
        Proyecto resultProject = this.proyectoMapper.proyectoCreationDTOToProyecto(proyectoCreationDTO);
        resultProject.setTutor(personal);

        resultProject.setStatus(status);

        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        estudiante.setHorasProgreso(250);

        var expectedProyectoEstudiante = new ProyectoEstudiante(estudiante, resultProject, false);
        expectedProyectoEstudiante.setId(1);
        resultProject.setProyectoEstudianteSet(Set.of(expectedProyectoEstudiante));
        resultProject.setId(1);
        resultProject.setFechaCreacion(LocalDateTime.now());

        RequerimientoIdView requerimiento = () -> 11;
        var doc = new Documento("doc", "", LocalDateTime.now());
        var requerimientoObj = new Requerimiento(1, true, 2, doc);

        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> personalIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Proyecto> proyectoCaptor = ArgumentCaptor.forClass(Proyecto.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> requerimientoCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.entityManager.getReference(ArgumentMatchers.<Class<Estudiante>>any(), Mockito.anyString())).thenReturn(estudiante);
        Mockito.when(this.entityManager.getReference(Personal.class, 1)).thenReturn(personal);
        Mockito.when(this.entityManager.getReference(Status.class, 1)).thenReturn(status);
        Mockito.when(this.entityManager.getReference(Requerimiento.class, 11)).thenReturn(requerimientoObj);
        Mockito.when(this.proyectoRepository.save(Mockito.any(Proyecto.class))).thenReturn(resultProject);
        Mockito.when(this.requerimientoRepository.findAllProjectedBy()).thenReturn(List.of(requerimiento));

        var result = this.proyectoService.save(proyectoCreationDTO);

        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Estudiante.class), carnetCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Personal.class), personalIdCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Status.class), statusCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Requerimiento.class), requerimientoCaptor.capture());
        Mockito.verify(this.proyectoRepository, Mockito.times(1)).save(proyectoCaptor.capture());
        Mockito.verify(this.requerimientoRepository, Mockito.times(1)).findAllProjectedBy();

        var expectedDto = new ProyectoCreationDTO.ProyectoDTO(1, "Project", 150,
                true, "Steve", Set.of(new EstudianteDTO(1, "ZH15002", 250, false)),
                LocalDateTime.now(), null, "DummyStatus");

        assertNotNull(result);
        assertEquals(result, expectedDto);
        resultProject.setId(null);
        assertEquals(resultProject, proyectoCaptor.getValue());
        assertEquals(carnet, carnetCaptor.getValue());
        assertEquals(1, personalIdCaptor.getValue());
        assertEquals(statusCaptor.getValue(), status.getId());
        assertEquals(requerimientoCaptor.getValue(), requerimiento.getId());

    }

    @Test
    void testUpdate(){
        var nombre = "Test";
        var personal = new Personal();
        personal.setNombre("Mario");
        personal.setId(10);

        var proyecto = new Proyecto();
        proyecto.setId(2);
        proyecto.setDuracion(300);
        proyecto.setNombre(nombre);
        proyecto.setInterno(true);
        proyecto.setTutor(personal);

        var estudiante = new Estudiante();
        estudiante.setCarnet("ab12345");
        estudiante.setHorasProgreso(250);
        estudiante.setServicioCompleto(false);

        var expectedProyectoEstudiante = new ProyectoEstudiante(estudiante, proyecto, false);
        expectedProyectoEstudiante.setId(1);

        proyecto.setProyectoEstudianteSet(Set.of(expectedProyectoEstudiante));
        proyecto.setFechaCreacion(LocalDateTime.now());
        proyecto.setFechaModificacion(LocalDateTime.now());
        proyecto.setStatus(new Status(1, "dummyValue", "dummyValue"));

        var creationDto = new ProyectoCreationDTO("Test2", 350, true, 10, List.of("ab12345"));

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> idPersonal = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Proyecto> proyectoCaptor = ArgumentCaptor.forClass(Proyecto.class);

        var expected = new ProyectoCreationDTO.ProyectoDTO(2, "Test2", 350, true, "Mario", Set.of(new EstudianteDTO(1, "ab12345", 250, false)), LocalDateTime.now(), LocalDateTime.now(), "dummyValue");

        Mockito.when(this.proyectoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(proyecto));
        Mockito.when(this.proyectoRepository.save(Mockito.any(Proyecto.class))).thenReturn(proyecto);
        Mockito.when(this.entityManager.getReference(Personal.class, 10)).thenReturn(personal);


        var result = this.proyectoService.update(creationDto, 5);

        Mockito.verify(this.proyectoRepository, Mockito.times(1)).findById(idCaptor.capture());
        Mockito.verify(this.proyectoRepository, Mockito.times(1)).save(proyectoCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Personal.class), idPersonal.capture());

        assertNotNull(result);
        assertEquals(expected, result);
        assertEquals(5, idCaptor.getValue());
        assertEquals(proyecto, proyectoCaptor.getValue());
        assertEquals(10, idPersonal.getValue());

    }
}