package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteControllerTest {

    public static final String CARNET = "zh15002";

    @Mock
    EstudianteService estudianteService;

    @Mock
    ProyectoService proyectoService;

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
        Mockito.when(this.estudianteService.findByCarnet(Mockito.anyString())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(carnet)) // carnet length doesn't match
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findAll() throws Exception {
        int pageParam = 2, sizeParam = 10;
        boolean isCompleteParam = true;
        List<EstudianteDTO> lista = List.of(new EstudianteDTO(), new EstudianteDTO());
        Mockito.when(this.estudianteService.findAllByServicio(
                pageParam, sizeParam, true))
                .thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(pageParam))
                .param("size", String.valueOf(sizeParam))
                .param("isComplete", isCompleteParam ? "si" : "no"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.estudiantes",
                        IsCollectionWithSize.hasSize(lista.size())));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isComplete = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(this.estudianteService, Mockito.times(1))
                .findAllByServicio(page.capture(), size.capture(), isComplete.capture());
        assertEquals(pageParam, page.getValue());
        assertEquals(sizeParam, size.getValue());
        assertEquals(isCompleteParam, isComplete.getValue());
    }

    @Test
    void findAllWithNoParams() throws Exception {
        int pageParam = 1, sizeParam = 10;
        boolean isCompleteParam = true;
        List<EstudianteDTO> lista = List.of(new EstudianteDTO(), new EstudianteDTO());
        Mockito.when(this.estudianteService.findAllByServicio(
                pageParam, sizeParam, true))
                .thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.estudiantes",
                        IsCollectionWithSize.hasSize(lista.size())));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isComplete = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(this.estudianteService, Mockito.times(1))
                .findAllByServicio(page.capture(), size.capture(), isComplete.capture());
        assertEquals(pageParam, page.getValue());
        assertEquals(sizeParam, size.getValue());
        assertEquals(isCompleteParam, isComplete.getValue());
    }

    @Test
    void whenBadIsCompleteValueIsGivenThenAnExceptionIsThrown() throws Exception {
        List<EstudianteDTO> lista = List.of(new EstudianteDTO(), new EstudianteDTO());
        Mockito.when(this.estudianteService.findAllByServicio(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isComplete", "something"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void post() throws Exception {
        String projectName = "Test project name";
        ProyectoCreationDTO proyectoCreationDTO = new ProyectoCreationDTO(projectName, 250, true, 10);
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
}