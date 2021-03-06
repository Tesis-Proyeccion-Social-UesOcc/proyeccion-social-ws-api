package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.*;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.service.EstadoRequerimientoEstudianteService;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteControllerTest {

    public static final String CARNET = "zh15002";
    public static final int PAGE = 5;
    public static final int SIZE = 10;

    @Mock
    EstudianteService estudianteService;

    @Mock
    ProyectoService proyectoService;

    @Mock
    EstadoRequerimientoEstudianteService estadoRequerimientoEstudianteService;

    @Mock
    CertificadoService certificadoService;

    @Mock
    ApplicationEventPublisher publisher;

    @InjectMocks
    EstudianteController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseExceptionCatcher())
                .build();
    }

    @Test
    void getOne() throws Exception {
        EstudianteDTO estudiante = new EstudianteDTO();
        estudiante.setCarnet(CARNET);
        Mockito.when(this.estudianteService.findByCarnet(CARNET)).thenReturn(estudiante);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET)).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carnet", CoreMatchers.is(CARNET)));
    }

    @Test
    void badRequestWhenInvalidCarnetIsProvided() throws Exception {
        String carnet = "ZH15005";
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        Mockito.when(this.estudianteService.findByCarnet(Mockito.anyString())).thenThrow(new IllegalArgumentException("Error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(carnet)) // carnet length doesn't match
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findAll() throws Exception {
        int pageParam = 2, sizeParam = 10;
        boolean isCompleteParam = true;
        var lista1 = List.of(new EstudianteDTO(), new EstudianteDTO());
        var lista2 = List.of(new Estudiante(), new Estudiante());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);

        Mockito.when(this.estudianteService.findAllByServicio(
                pageParam, sizeParam, true))
                .thenReturn(toReturn);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(pageParam))
                .param("size", String.valueOf(sizeParam))
                .param("isComplete", isCompleteParam ? "si" : "no"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.content",
                        IsCollectionWithSize.hasSize(toReturn.getContent().size())));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isComplete = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(this.estudianteService, Mockito.times(1))
                .findAllByServicio(page.capture(), size.capture(), isComplete.capture());
        assertEquals(pageParam, page.getValue());
        assertEquals(sizeParam, size.getValue());
        assertTrue(isComplete.getValue());
    }

    @Test
    void findAllWithNoParams() throws Exception {
        int pageParam = 0, sizeParam = 10;
        boolean isCompleteParam = true;
        var lista1 = List.of(new EstudianteDTO(), new EstudianteDTO());
        var lista2 = List.of(new Estudiante(), new Estudiante());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);

        Mockito.when(this.estudianteService.findAllByServicio(
                pageParam, sizeParam, true))
                .thenReturn(toReturn);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.content",
                        IsCollectionWithSize.hasSize(toReturn.getContent().size())));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isComplete = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(this.estudianteService, Mockito.times(1))
                .findAllByServicio(page.capture(), size.capture(), isComplete.capture());
        assertEquals(pageParam, page.getValue());
        assertEquals(sizeParam, size.getValue());
    assertTrue(isComplete.getValue());
    }

    @Test
    void whenBadIsCompleteValueIsGivenThenAnExceptionIsThrown() throws Exception {
        var lista1 = List.of(new EstudianteDTO(), new EstudianteDTO());
        var lista2 = List.of(new Estudiante(), new Estudiante());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE), lista2.size()), lista1);

        Mockito.when(this.estudianteService.findAllByServicio(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(toReturn);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isComplete", "something"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testStudentProjectByName() throws Exception{
        ProyectoCreationDTO.ProyectoDTO dto1 = new ProyectoCreationDTO.ProyectoDTO(1, "Project1", 100, true, "Steve Jobs", Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now(), "dummy");

        var carnetCaptor = ArgumentCaptor.forClass(String.class);
        var projectNameCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.proyectoService.findByCarnetAndProjectName(Mockito.anyString(), Mockito.anyString())).thenReturn(dto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET).concat("/proyectos/single"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("projectName", "Project1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(dto1.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duracion", CoreMatchers.is(dto1.getDuracion())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.interno", CoreMatchers.is(dto1.isInterno())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personal", CoreMatchers.is(dto1.getPersonal())));

        Mockito.verify(this.proyectoService, Mockito.times(1)).findByCarnetAndProjectName(carnetCaptor.capture(), projectNameCaptor.capture());

        assertEquals(carnetCaptor.getValue(), CARNET);
        assertEquals(projectNameCaptor.getValue(), dto1.getNombre());
    }

    @Test
    void projectsByStudentID() throws Exception{
        String status = "3";
        ProyectoCreationDTO.ProyectoDTO dto1 = new ProyectoCreationDTO.ProyectoDTO(1, "Project1", 100, true, "Steve Jobs", Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now(), "dummy");
        ProyectoCreationDTO.ProyectoDTO dto2 = new ProyectoCreationDTO.ProyectoDTO(2, "Project2", 200, true, "Steve Gerard", Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now(), "dummy");

        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> sizeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);

        var lista1 = List.of(dto1, dto2);
        var lista2 = List.of(new Proyecto(), new Proyecto());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);

        Mockito.when(this.proyectoService
                .findProyectosByEstudiante(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(toReturn);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET).concat("/proyectos"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10")
                .param("status", status))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",
                            IsCollectionWithSize.hasSize(toReturn.getContent().size())));
        Mockito.verify(this.proyectoService, Mockito.times(1))
                .findProyectosByEstudiante(pageCaptor.capture(), sizeCaptor.capture(), carnetCaptor.capture(), statusCaptor.capture());
        assertEquals(PAGE, pageCaptor.getValue());
        assertEquals(SIZE, sizeCaptor.getValue());
        assertEquals(Integer.parseInt(status), statusCaptor.getValue());
        assertEquals(CARNET, carnetCaptor.getValue());
    }

    @Test
    void projectsByStudentIDWIthDocuments() throws Exception{
        String status = "1";
        var docs = Set.of(new SimpleDocumentDTO(1,1,"mydoc", true, true, null, null));
        var dto1 = new PendingProjectDTO(
                1, "Project1", 100, true, "Steve Jobs",
                Collections.emptySet(), docs, LocalDateTime.now(), LocalDateTime.now(), "dummy");

        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> sizeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);

        var lista1 = List.of(dto1);
        var lista2 = List.of(new Proyecto());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);

        Mockito.when(this.proyectoService
                .findProyectosPendientesByEstudiante(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(toReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET).concat("/proyectos"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10")
                .param("status", status))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",
                        IsCollectionWithSize.hasSize(toReturn.getContent().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].documentos[0].nombre", CoreMatchers.is("mydoc")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].documentos[0].entregado", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].documentos[0].aprobado", CoreMatchers.is(true)));

        Mockito.verify(this.proyectoService, Mockito.times(1))
                .findProyectosPendientesByEstudiante(pageCaptor.capture(), sizeCaptor.capture(), carnetCaptor.capture(), statusCaptor.capture());

        assertEquals(PAGE, pageCaptor.getValue());
        assertEquals(SIZE, sizeCaptor.getValue());
        assertEquals(Integer.parseInt(status), statusCaptor.getValue());
        assertEquals(CARNET, carnetCaptor.getValue());
    }

    @Test
    void testGetRequirementStatus() throws Exception{
        var project1 = new PendingProjectDTO(
                1, "test1", 150, true, "tutor1", null,
                Set.of(new SimpleDocumentDTO(1,1,"doc1", true, true, null, null)),
                null, null, "pendiente");

        var project2 = new ProyectoCreationDTO.ProyectoDTO(
                2, "test2", 150, true, "tutor2", null,
                null, null, "en proceso");

        var projects = List.of(project1, project2);
        Mockito.doReturn(projects).when(this.proyectoService).getRequirementsData(Mockito.anyString());

        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/abc12345/proyectos/estadoRequerimiento"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].nombre", CoreMatchers.is("test1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].status", CoreMatchers.is("pendiente")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].documentos[0].nombre", CoreMatchers.is("doc1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].nombre", CoreMatchers.is("test2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].status", CoreMatchers.is("en proceso")));

    }

    @Test
    void addDocument() throws Exception{
        String dateStr = "2019-02-18", requerimientoId = "10";
        EstadoRequerimientoEstudianteDTO dto = new EstadoRequerimientoEstudianteDTO(true, Date.valueOf(dateStr));
        Optional<EstadoRequerimientoEstudianteDTO> toReturnObj = Optional.of(dto);

        ArgumentCaptor<Integer> proyectoEstuanteIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> requerimientoIdCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.estadoRequerimientoEstudianteService.save(Mockito.anyInt(), Mockito.anyInt())).thenReturn(toReturnObj);

        mockMvc.perform(MockMvcRequestBuilders.post("/estudiantes/123".concat("/documentos/").concat(requerimientoId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaEntrega", CoreMatchers.is(dateStr)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aprobado", CoreMatchers.is(true)));

        Mockito.verify(this.estadoRequerimientoEstudianteService, Mockito.times(1))
                .save(proyectoEstuanteIdCaptor.capture(), requerimientoIdCaptor.capture());

        assertEquals(123, proyectoEstuanteIdCaptor.getValue());
        assertEquals(Integer.parseInt(requerimientoId), requerimientoIdCaptor.getValue());
    }

    @Test
    void addDocumentWithEmptyOptional() throws Exception{
        Mockito.when(this.estadoRequerimientoEstudianteService.save(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.post("/estudiantes/1/documentos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.containsString("went wrong")));
    }

    @Test
    void getStudentDocuments() throws Exception{
        String dateStr1 = "2019-12-01", dateStr2 = "2018-11-01";
        EstadoRequerimientoEstudianteDTO dto1 = new EstadoRequerimientoEstudianteDTO(false, Date.valueOf(dateStr1));
        EstadoRequerimientoEstudianteDTO dto2 = new EstadoRequerimientoEstudianteDTO(true, Date.valueOf(dateStr2));
        var lista1 = List.of(dto1, dto2);
        var lista2 = List.of(new EstadoRequerimientoEstudiante(), new EstadoRequerimientoEstudiante());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);


        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> sizeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> isAprobadoCaptor = ArgumentCaptor.forClass(Boolean.class);

        Mockito.when(this.estadoRequerimientoEstudianteService
                .findAllByCarnet(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(toReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET).concat("/documentos"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10")
                .param("aprobado", "si"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", IsCollectionWithSize.hasSize(toReturn.getContent().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].fechaEntrega", CoreMatchers.is(dateStr1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].fechaEntrega", CoreMatchers.is(dateStr2)));

        Mockito.verify(this.estadoRequerimientoEstudianteService, Mockito.times(1))
                .findAllByCarnet(pageCaptor.capture(), sizeCaptor.capture(), carnetCaptor.capture(), isAprobadoCaptor.capture());

        assertEquals(PAGE, pageCaptor.getValue());
        assertEquals(SIZE, sizeCaptor.getValue());
        assertEquals(CARNET, carnetCaptor.getValue());
        assertTrue(isAprobadoCaptor.getValue());

    }

    @Test
    void getStudentDocumentsWithInvalidAprobadoValue() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET).concat("/documentos"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10")
                .param("aprobado", "something"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getCertificate() throws Exception{
        String dateStr1 = "2019-12-01", dateStr2 = "2018-11-12";
        CertificadoCreationDTO.CertificadoDTO dto1 = new CertificadoCreationDTO.CertificadoDTO(
                1, "Proyecto 1", "http://www.google.com/certificado1", LocalDateTime.of(
                        LocalDate.of(2019, 12,1), LocalTime.now()));
        CertificadoCreationDTO.CertificadoDTO dto2 = new CertificadoCreationDTO.CertificadoDTO(
                1, "Proyecto 2", "http://www.google.com/certificado2",  LocalDateTime.of(
                LocalDate.of(2018, 11,12), LocalTime.now()));

        var lista1 = List.of(dto1, dto2);
        var lista2 = List.of(new Certificado(), new Certificado());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);


        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> sizeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.certificadoService.findAllByCarnet(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn(toReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(CARNET).concat("/certificados"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", IsCollectionWithSize.hasSize(toReturn.getContent().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].fechaExpedicion", CoreMatchers.is(dateStr1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].fechaExpedicion", CoreMatchers.is(dateStr2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].uri", Matchers.containsInAnyOrder(
                        "http://www.google.com/certificado1", "http://www.google.com/certificado2"
                )))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].proyecto", Matchers.containsInAnyOrder(
                        "Proyecto 1", "Proyecto 2"
                )));
    }
}