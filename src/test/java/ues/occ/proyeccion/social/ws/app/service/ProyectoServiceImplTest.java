package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ues.occ.proyeccion.social.ws.app.dao.*;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.ProyectoMapper;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoServiceImplTest {

    private static final int PAGE = 5;
    private static final int SIZE = 10;


    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private ProyectoEstudianteRepository proyectoEstudianteRepository;

    @Mock
    EntityManager entityManager;

    @Captor
    ArgumentCaptor<List<ProyectoEstudiante>> proyectoEstudianteCaptor;

    private ProyectoServiceImpl proyectoService;
    private final ProyectoMapper proyectoMapper = ProyectoMapper.INSTANCE;
    static Proyecto proyecto1;
    static Proyecto proyecto2;

    @BeforeAll
    static void setUpForClass(){
        proyecto1 = new Proyecto();
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
        proyecto2.setInterno(true);
        proyecto2.setTutor(new Personal());
        proyecto2.setProyectoEstudianteSet(proyectoEstudiantes);

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proyectoService = new ProyectoServiceImpl(proyectoRepository, proyectoEstudianteRepository, proyectoMapper, entityManager);
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

        Mockito.when(this.proyectoRepository.findAllByStatus(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findAllByStatus(5, 10, 1);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatus(statusCaptor.capture(), pageableArgumentCaptor.capture());

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

        Mockito.when(this.proyectoRepository.findAllByStatus(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findAllPending(5, 10);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatus(statusCaptor.capture(), pageableArgumentCaptor.capture());

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

        Mockito.when(this.proyectoRepository.findAllByStatusAndProyectoEstudianteSet_Estudiante_Carnet(
                Mockito.anyInt(), Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findProyectosByEstudiante(5, 10, "zh", 3);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByStatusAndProyectoEstudianteSet_Estudiante_Carnet(
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
    void testSave(){
        String carnet = "ZH15002";
        Personal personal = new Personal();
        personal.setId(1);
        personal.setNombre("Steve");

        var carnets = List.of(carnet);

        ProyectoCreationDTO proyectoCreationDTO = new ProyectoCreationDTO("Project", 150, true, 1, carnets);
        Proyecto resultProject = this.proyectoMapper.proyectoCreationDTOToProyecto(proyectoCreationDTO);
        resultProject.setTutor(personal);


        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        estudiante.setHorasProgreso(250);

        var expectedProyectoEstudiante = new ProyectoEstudiante(estudiante, resultProject);
        resultProject.setProyectoEstudianteSet(Set.of(expectedProyectoEstudiante));
        resultProject.setId(1);


        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> personalIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Proyecto> proyectoCaptor = ArgumentCaptor.forClass(Proyecto.class);

        Mockito.when(this.entityManager.getReference(ArgumentMatchers.<Class<Estudiante>>any(), Mockito.anyString())).thenReturn(estudiante);
        Mockito.when(this.entityManager.getReference(Personal.class, 1)).thenReturn(personal);
        Mockito.when(this.proyectoRepository.save(Mockito.any(Proyecto.class))).thenReturn(resultProject);
        Mockito.when(this.proyectoEstudianteRepository.saveAll(Mockito.anyList())).thenReturn(null);

        var result = this.proyectoService.save(proyectoCreationDTO);

        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Estudiante.class), carnetCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Personal.class), personalIdCaptor.capture());
        Mockito.verify(this.proyectoRepository, Mockito.times(1)).save(proyectoCaptor.capture());
        Mockito.verify(this.proyectoEstudianteRepository, Mockito.times(1)).saveAll(proyectoEstudianteCaptor.capture());

        var expectedDto = new ProyectoCreationDTO.ProyectoDTO(1, "Project", 150,
                true, "Steve", Set.of(new EstudianteDTO("ZH15002", 250, false)));

        assertNotNull(result);
        assertEquals(result, expectedDto);
        resultProject.setId(null);
        assertEquals(resultProject, proyectoCaptor.getValue());
        assertEquals(carnet, carnetCaptor.getValue());
        assertEquals(1, personalIdCaptor.getValue());
        assertEquals(List.of(expectedProyectoEstudiante), proyectoEstudianteCaptor.getValue());

    }
}