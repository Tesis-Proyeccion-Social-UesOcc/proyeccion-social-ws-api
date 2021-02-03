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
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
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
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(PAGE, SIZE),  lista2.size()), lista1);

        Mockito.when(this.estudianteService.findAllByServicio(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(toReturn);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isComplete", "something"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createProject() throws Exception {
        String projectName = "Test project name";
        ProyectoCreationDTO proyectoCreationDTO = new ProyectoCreationDTO(1, projectName, 250, true, 10);
        EstudianteDTO estudiante = new EstudianteDTO();
        estudiante.setCarnet(CARNET);
        Mockito.when(this.estudianteService.findByCarnet(CARNET)).thenReturn(estudiante);
        Mockito.when(this.proyectoService.save(CARNET, proyectoCreationDTO)).thenReturn(proyectoCreationDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/estudiantes/".concat(CARNET).concat("/proyectos"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(proyectoCreationDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(projectName)));

        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ProyectoCreationDTO> projectDTOCaptor = ArgumentCaptor.forClass(ProyectoCreationDTO.class);

        Mockito.verify(this.proyectoService, Mockito.times(1)).save(carnetCaptor.capture(), projectDTOCaptor.capture());

        assertEquals(CARNET, carnetCaptor.getValue());
        assertEquals(proyectoCreationDTO, projectDTOCaptor.getValue());

    }

    @Test
    void projectsByStudentID() throws Exception{
        String status = "3";
        ProyectoCreationDTO.ProyectoDTO dto1 = new ProyectoCreationDTO.ProyectoDTO(1, "Project1", 100, true, "Steve Jobs", Collections.emptySet());
        ProyectoCreationDTO.ProyectoDTO dto2 = new ProyectoCreationDTO.ProyectoDTO(2, "Project2", 200, true, "Steve Gerard", Collections.emptySet());

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
    void addDocument() throws Exception{
        String dateStr = "2019-02-18", requerimientoId = "10";
        EstadoRequerimientoEstudianteDTO dto = new EstadoRequerimientoEstudianteDTO(true, Date.valueOf(dateStr));
        Optional<EstadoRequerimientoEstudianteDTO> toReturnObj = Optional.of(dto);

        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> requerimientoIdCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.estadoRequerimientoEstudianteService.save(Mockito.anyString(), Mockito.anyInt())).thenReturn(toReturnObj);

        mockMvc.perform(MockMvcRequestBuilders.post("/estudiantes/".concat(CARNET).concat("/documentos/").concat(requerimientoId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaEntrega", CoreMatchers.is(dateStr)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aprobado", CoreMatchers.is(true)));

        Mockito.verify(this.estadoRequerimientoEstudianteService, Mockito.times(1))
                .save(carnetCaptor.capture(), requerimientoIdCaptor.capture());

        assertEquals(CARNET, carnetCaptor.getValue());
        assertEquals(Integer.parseInt(requerimientoId), requerimientoIdCaptor.getValue());
    }

    @Test
    void addDocumentWithEmptyOptional() throws Exception{
        Mockito.when(this.estadoRequerimientoEstudianteService.save(Mockito.anyString(), Mockito.anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.post("/estudiantes/someCarnet/documentos/1")
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