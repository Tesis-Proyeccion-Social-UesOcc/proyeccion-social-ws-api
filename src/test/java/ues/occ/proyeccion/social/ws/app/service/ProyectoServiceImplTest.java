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
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
        proyecto1.setEncargadoExterno(personalExterno);
        proyecto1.setTutor(new Personal());
        proyecto2 = new Proyecto();
        proyecto2.setInterno(true);
        proyecto2.setTutor(new Personal());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proyectoService = new ProyectoServiceImpl(proyectoRepository, proyectoEstudianteRepository, proyectoMapper, entityManager);
    }

    @Test
    void testFindById() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(5);
        Optional<Proyecto> proyecto_ = Optional.of(proyecto);
        Mockito.when(this.proyectoRepository.findById(Mockito.anyInt()))
                .thenReturn(proyecto_);
        Proyecto resultObject = this.proyectoService.findById(1);
        assertNotNull(resultObject);
        assertEquals(resultObject.getId(), 5);
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
        assertEquals(5, captured.getPageNumber());
        assertEquals(10, captured.getPageSize());
        assertNotNull(result);
        //prueba con content
        assertEquals(2, result.getContent().size());

    }

    @Test
    void testFindAllByStatus(){
        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.proyectoRepository.findAllByProyectoEstudianteSet_StatusId(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findAllByStatus(5, 10, 1);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByProyectoEstudianteSet_StatusId(statusCaptor.capture(), pageableArgumentCaptor.capture());

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

        Mockito.when(this.proyectoRepository.findAllByProyectoEstudianteSet_Empty(Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findAllPending(5, 10);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByProyectoEstudianteSet_Empty(pageableArgumentCaptor.capture());

        assertNotNull(result);
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        // prueba con content
        assertEquals(2, result.getContent().size());
        assertFalse(result.getContent().get(0).isInterno());
        assertEquals(result.getContent().get(0).getPersonal(), "text");
        assertTrue(result.getContent().get(1).isInterno());

    }

    @Test
    void testFindProyectosByEstudiante(){

        List<Proyecto> data = List.of(proyecto1, proyecto2);
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Integer> statusArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.proyectoRepository.findAllByProyectoEstudianteSet_Estudiante_CarnetAndProyectoEstudianteSet_Status_id(
                Mockito.anyString(), Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(page);

        var result = this.proyectoService.findProyectosByEstudiante(5, 10, "zh", 3);

        Mockito.verify(this.proyectoRepository, Mockito.times(1))
                .findAllByProyectoEstudianteSet_Estudiante_CarnetAndProyectoEstudianteSet_Status_id(
                        carnetArgumentCaptor.capture(), statusArgumentCaptor.capture(), pageableArgumentCaptor.capture()
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

        ProyectoCreationDTO proyectoCreationDTO = new ProyectoCreationDTO(1, "Project", 150, true, 1);
        Proyecto resultProject = this.proyectoMapper.proyectoCreationDTOToProyecto(proyectoCreationDTO);
        resultProject.setTutor(personal);

        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);

        Status status = new Status();
        status.setId(1);

        ProyectoEstudiante proyectoEstudiante = new ProyectoEstudiante(estudiante, resultProject, status);

        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> personalIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> statusIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Proyecto> proyectoCaptor = ArgumentCaptor.forClass(Proyecto.class);
        ArgumentCaptor<ProyectoEstudiante> proyectoEstudianteCaptor = ArgumentCaptor.forClass(ProyectoEstudiante.class);


        Mockito.when(this.entityManager.getReference(ArgumentMatchers.<Class<Estudiante>>any(), Mockito.anyString())).thenReturn(estudiante);
        Mockito.when(this.entityManager.getReference(Personal.class, 1)).thenReturn(personal);
        Mockito.when(this.entityManager.getReference(Status.class, 1)).thenReturn(status);
        Mockito.when(this.proyectoRepository.save(Mockito.any(Proyecto.class))).thenReturn(resultProject);
        Mockito.when(this.proyectoEstudianteRepository.save(Mockito.any(ProyectoEstudiante.class))).thenReturn(null);

        ProyectoCreationDTO result = this.proyectoService.save(carnet, proyectoCreationDTO);

        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Estudiante.class), carnetCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Personal.class), personalIdCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1)).getReference(ArgumentMatchers.eq(Status.class), statusIdCaptor.capture());
        Mockito.verify(this.proyectoRepository, Mockito.times(1)).save(proyectoCaptor.capture());
        Mockito.verify(this.proyectoEstudianteRepository, Mockito.times(1)).save(proyectoEstudianteCaptor.capture());

        assertNotNull(result);
        assertEquals(result, proyectoCreationDTO);
        assertEquals(carnet, carnetCaptor.getValue());
        assertEquals(1, personalIdCaptor.getValue());
        assertEquals(1, statusIdCaptor.getValue());
        assertEquals(resultProject, proyectoCaptor.getValue());
        assertEquals(proyectoEstudiante, proyectoEstudianteCaptor.getValue());

    }
}