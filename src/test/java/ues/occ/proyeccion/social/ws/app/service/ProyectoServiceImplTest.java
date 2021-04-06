package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeAll;
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
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.StatusDTO;
import ues.occ.proyeccion.social.ws.app.repository.DocumentoRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
    private DocumentoRepository documentoRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    EntityManager entityManager;

    private ProyectoServiceImpl proyectoService;

    static Proyecto proyecto1;
    static Proyecto proyecto2;

    @BeforeAll
    static void setUpForClass(){
        proyecto1 = new Proyecto();
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
        Set<ProyectoEstudiante> proyectoEstudiantes = Set.of(proyectoEstudiante1, proyectoEstudiante2);
        proyecto1.setEncargadoExterno(personalExterno);
        proyecto1.setTutor(new Personal());
        proyecto1.setProyectoEstudianteSet(proyectoEstudiantes);
        proyecto2 = new Proyecto();
        proyecto2.setNombre("proyecto2");
        proyecto2.setInterno(true);
        proyecto2.setTutor(new Personal());
        proyecto2.setProyectoEstudianteSet(proyectoEstudiantes);

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proyectoService = new ProyectoServiceImpl(proyectoRepository, documentoRepository, proyectoMapper, estudianteRepository, entityManager);
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

        var estudianteDTO1 = new EstudianteDTO("ZH15002", 500, true);
        var estudianteDTO2 = new EstudianteDTO("AB15002", 200, false);
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
        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());

        var document = new Documento("doc", "my doc", LocalDateTime.now());
        var requerimiento = new Requerimiento(1, true, 2, document);
        var student = new Estudiante("zh15002", 200, false);
        var projectStudent = new ProyectoEstudiante(student, proyecto1, true);
        projectStudent.addRequerimiento(requerimiento, false);

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
    void testSave(){
        String carnet = "ZH15002";
        Personal personal = new Personal();
        personal.setId(1);
        personal.setNombre("Steve");

        var status = new Status(1, "DummyStatus", "DummyDesc");

        var carnets = List.of(carnet);

        ProyectoCreationDTO proyectoCreationDTO = new ProyectoCreationDTO("Project", 150, true, 1, carnets, new StatusDTO(1, "DummyStatus", "DummyDesc"));
        Proyecto resultProject = this.proyectoMapper.proyectoCreationDTOToProyecto(proyectoCreationDTO);
        resultProject.setTutor(personal);

        resultProject.setStatus(status);

        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        estudiante.setHorasProgreso(250);

        var expectedProyectoEstudiante = new ProyectoEstudiante(estudiante, resultProject, false);
        resultProject.setProyectoEstudianteSet(Set.of(expectedProyectoEstudiante));
        resultProject.setId(1);
        resultProject.setFechaCreacion(LocalDateTime.now());


        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> personalIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Proyecto> proyectoCaptor = ArgumentCaptor.forClass(Proyecto.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.entityManager.getReference(ArgumentMatchers.<Class<Estudiante>>any(), Mockito.anyString())).thenReturn(estudiante);
        Mockito.when(this.entityManager.getReference(Personal.class, 1)).thenReturn(personal);
        Mockito.when(this.entityManager.getReference(Status.class, 1)).thenReturn(status);
        Mockito.when(this.proyectoRepository.save(Mockito.any(Proyecto.class))).thenReturn(resultProject);

        var result = this.proyectoService.save(proyectoCreationDTO);

        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Estudiante.class), carnetCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Personal.class), personalIdCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Status.class), statusCaptor.capture());
        Mockito.verify(this.proyectoRepository, Mockito.times(1)).save(proyectoCaptor.capture());

        var expectedDto = new ProyectoCreationDTO.ProyectoDTO(1, "Project", 150,
                true, "Steve", Set.of(new EstudianteDTO("ZH15002", 250, false)),
                LocalDateTime.now(), null, "DummyStatus");

        assertNotNull(result);
        assertEquals(result, expectedDto);
        resultProject.setId(null);
        assertEquals(resultProject, proyectoCaptor.getValue());
        assertEquals(carnet, carnetCaptor.getValue());
        assertEquals(1, personalIdCaptor.getValue());
        assertEquals(statusCaptor.getValue(), status.getId());

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

        proyecto.setProyectoEstudianteSet(Set.of(expectedProyectoEstudiante));
        proyecto.setFechaCreacion(LocalDateTime.now());
        proyecto.setFechaModificacion(LocalDateTime.now());
        proyecto.setStatus(new Status(1, "dummyValue", "dummyValue"));

        var creationDto = new ProyectoCreationDTO("Test2", 350, true, 10, List.of("ab12345"), new StatusDTO());

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> idPersonal = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Proyecto> proyectoCaptor = ArgumentCaptor.forClass(Proyecto.class);

        var expected = new ProyectoCreationDTO.ProyectoDTO(2, "Test2", 350, true, "Mario", Set.of(new EstudianteDTO("ab12345", 250, false)), LocalDateTime.now(), LocalDateTime.now(), "dummyValue");

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