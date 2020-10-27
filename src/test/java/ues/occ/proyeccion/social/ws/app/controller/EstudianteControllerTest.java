package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteControllerTest {

    @Mock
    EstudianteService service;

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
        String carnet = "ZH15002";
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        Mockito.when(this.service.findByCarnet(carnet)).thenReturn(estudiante);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(carnet)).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carnet", CoreMatchers.is(carnet)));
    }

    @Test
    void badRequestWhenInvalidCarnetIsProvided() throws Exception {
        String carnet = "ZH15005";
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        Mockito.when(this.service.findByCarnet(Mockito.anyString())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(carnet)) // carnet length doesn't match
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findAll() throws Exception {
        int pageParam = 2, sizeParam = 10;
        boolean isCompleteParam = true;
        List lista = List.of(new Estudiante(), new Estudiante());
        Mockito.when(this.service.findAllByServicio(
                pageParam, sizeParam, true))
                .thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", String.valueOf(pageParam))
        .param("size", String.valueOf(sizeParam))
        .param("isComplete", isCompleteParam?"si":"no"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$",
                        IsCollectionWithSize.hasSize(lista.size())));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isComplete = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(this.service, Mockito.times(1))
                .findAllByServicio(page.capture(), size.capture(), isComplete.capture());
        assertEquals(pageParam, page.getValue());
        assertEquals(sizeParam, size.getValue());
        assertEquals(isCompleteParam, isComplete.getValue());
    }

    @Test
    void findAllWithNoParams() throws Exception{
        int pageParam = 1, sizeParam = 10;
        boolean isCompleteParam = true;
        List lista = List.of(new Estudiante(), new Estudiante());
        Mockito.when(this.service.findAllByServicio(
                pageParam, sizeParam, true))
                .thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$",
                        IsCollectionWithSize.hasSize(lista.size())));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> isComplete = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(this.service, Mockito.times(1))
                .findAllByServicio(page.capture(), size.capture(), isComplete.capture());
        assertEquals(pageParam, page.getValue());
        assertEquals(sizeParam, size.getValue());
        assertEquals(isCompleteParam, isComplete.getValue());
    }

    @Test
    void whenBadIsCompleteValueIsGivenThenAnExceptionIsThrown() throws Exception{
        List lista = List.of(new Estudiante(), new Estudiante());
        Mockito.when(this.service.findAllByServicio(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(lista);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isComplete", "something"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}