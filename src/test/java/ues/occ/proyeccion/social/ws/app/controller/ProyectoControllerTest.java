package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.EmbeddedStudentDTO;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.StatusDTO;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProyectoControllerTest {

    @Mock
    ProyectoService proyectoService;

    @Mock
    ApplicationEventPublisher publisher;

    @InjectMocks
    ProyectoController proyectoController;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(proyectoController)
                .setControllerAdvice(new RestResponseExceptionCatcher())
                .build();
    }

    @Test
    void getOne() throws Exception {
        var proyecto = new ProyectoCreationDTO.ProyectoDTO();
        String name = "project 64";
        proyecto.setId(12);
        proyecto.setNombre(name);
        Mockito.when(proyectoService.findById(Mockito.anyInt())).thenReturn(proyecto);
        mvc.perform(get("/proyectos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.estudiante.carnet", is(carnet)))
                .andExpect(jsonPath("$.nombre", is(name)));

    }

    @Test
    void getRange() throws Exception {
        Pageable request = PageRequest.of(1, 10);
        var lista1 = List.of(new ProyectoCreationDTO.ProyectoDTO(), new ProyectoCreationDTO.ProyectoDTO());
        var lista2 = List.of(new Proyecto(), new Proyecto());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(0, 10), lista2.size()), lista1);
        Mockito.when(proyectoService.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(toReturn);
        mvc.perform(get("/proyectos/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10"))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(proyectoService, Mockito.times(1)).findAll(
                page.capture(), size.capture()
        );
        Assertions.assertEquals(5, page.getValue());
        Assertions.assertEquals(10, size.getValue());
    }

    @Test
    void findAllByStatus() throws Exception {
        var lista1 = List.of(new ProyectoCreationDTO.ProyectoDTO(), new ProyectoCreationDTO.ProyectoDTO());
        var lista2 = List.of(new Proyecto(), new Proyecto());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(0, 10), lista2.size()), lista1);

        Mockito.when(proyectoService.findAllByStatus(5, 10, 7)).thenReturn(toReturn);
        mvc.perform(get("/proyectos/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10")
                .param("status", "7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", IsCollectionWithSize.hasSize(2)));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> status = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(proyectoService, Mockito.times(1)).findAllByStatus(
                page.capture(), size.capture(), status.capture()
        );
        Assertions.assertEquals(5, page.getValue());
        Assertions.assertEquals(10, size.getValue());
        Assertions.assertEquals(7, status.getValue());
    }

    @Test
    void findAllPending() throws Exception {
        var lista1 = List.of(new ProyectoCreationDTO.ProyectoDTO(), new ProyectoCreationDTO.ProyectoDTO());
        var lista2 = List.of(new Proyecto(), new Proyecto());
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(lista2, PageRequest.of(0, 10), lista2.size()), lista1);
        Mockito.when(proyectoService.findAllPending(5, 10)).thenReturn(toReturn);
        mvc.perform(get("/proyectos/pending")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", IsCollectionWithSize.hasSize(2)));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(proyectoService, Mockito.times(1)).findAllPending(
                page.capture(), size.capture()
        );
        Assertions.assertEquals(5, page.getValue());
        Assertions.assertEquals(10, size.getValue());
    }

    @Test
    void createTest() throws Exception{
        var proyecto = "Test";
        var estudiantes = List.of("zh15002");
        var body = ProyectoCreationDTO
                .builder().nombre(proyecto).duracion(200).interno(true)
                .personal(10).estudiantes(estudiantes).build();

        var estudiantesResponse = Set.of(new EmbeddedStudentDTO("zh15002", 300, false, true));

        var responseObj = ProyectoCreationDTO.ProyectoDTO.builder()
                .nombre(proyecto).duracion(200).interno(true)
                .personal("Steve").estudiantes(estudiantesResponse).build();

        Mockito.when(this.proyectoService.save(Mockito.any(ProyectoCreationDTO.class))).thenReturn(responseObj);

        mvc.perform(post("/proyectos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estudiantes[0].carnet", is("zh15002")))
                .andExpect(jsonPath("$.nombre", is(proyecto)));

        var captor = ArgumentCaptor.forClass(ProyectoCreationDTO.class);

        Mockito.verify(this.proyectoService, Mockito.times(1)).save(captor.capture());

        Assertions.assertEquals(captor.getValue(), body);
    }

    @Test
    void createTestWithInvalidBody() throws Exception{
        var proyecto = "Test";
        var estudiantes = List.of("zhd0022");
        var body = new ProyectoCreationDTO(proyecto, null, true, 10, estudiantes);

        var estudiantesResponse = Set.of(new EmbeddedStudentDTO("zh15002", 300, false, true));

        var responseObj = ProyectoCreationDTO.ProyectoDTO.builder()
                .nombre(proyecto).duracion(200).interno(true)
                .personal("Steve").estudiantes(estudiantesResponse).build();

        Mockito.when(this.proyectoService.save(Mockito.any(ProyectoCreationDTO.class))).thenReturn(responseObj);

        mvc.perform(post("/proyectos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.violations[*].message", Matchers
                        .containsInAnyOrder("Carnet is an alphanumeric ID with 7 characters only, i.e. AB12345",
                                "Mandatory param")));
//                .andExpect(jsonPath("$.violations[0].message", CoreMatchers.containsString("7 characters only")))
//                .andExpect(jsonPath("violations[1].message", containsString("Mandatory")));
    }

    @Test
    void updateTest() throws Exception{
        var proyecto = "Test";
        var estudiantes = List.of("zh15002");
        var body = ProyectoCreationDTO
                .builder().nombre(proyecto).duracion(200).interno(true)
                .personal(10).estudiantes(estudiantes).build();

        var estudiantesResponse = Set.of(new EmbeddedStudentDTO("zh15002", 300, false, true));

        var responseObj = ProyectoCreationDTO.ProyectoDTO.builder()
                .nombre(proyecto).duracion(200).interno(true)
                .personal("Steve").estudiantes(estudiantesResponse).build();

        Mockito.when(this.proyectoService.update(Mockito.any(ProyectoCreationDTO.class), Mockito.anyInt())).thenReturn(responseObj);

        mvc.perform(put("/proyectos/100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estudiantes[0].carnet", is("zh15002")))
                .andExpect(jsonPath("$.nombre", is(proyecto)));

        var captor = ArgumentCaptor.forClass(ProyectoCreationDTO.class);
        var idCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(this.proyectoService, Mockito.times(1)).update(captor.capture(), idCaptor.capture());

        Assertions.assertEquals(captor.getValue(), body);
        Assertions.assertEquals(idCaptor.getValue(), 100);

    }

    @Test
    void UpdateTestWithInvalidBody() throws Exception{
        var proyecto = "Test";
        var estudiantes = List.of("zhd0022");
        var body = new ProyectoCreationDTO(proyecto, null, true, 10, estudiantes);

        var estudiantesResponse = Set.of(new EmbeddedStudentDTO("zh15002", 300, false, true));

        var responseObj = ProyectoCreationDTO.ProyectoDTO.builder()
                .nombre(proyecto).duracion(200).interno(true)
                .personal("Steve").estudiantes(estudiantesResponse).build();

        Mockito.when(this.proyectoService.update(Mockito.any(ProyectoCreationDTO.class), Mockito.anyInt())).thenReturn(responseObj);

        mvc.perform(put("/proyectos/100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.violations[*].message", Matchers
                        .containsInAnyOrder("Carnet is an alphanumeric ID with 7 characters only, i.e. AB12345",
                                "Mandatory param")));

        Mockito.verify(this.proyectoService, Mockito.never()).update(Mockito.any(ProyectoCreationDTO.class), Mockito.anyInt());
    }
}