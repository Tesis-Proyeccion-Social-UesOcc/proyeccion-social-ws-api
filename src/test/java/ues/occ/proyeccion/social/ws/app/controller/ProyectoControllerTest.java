package ues.occ.proyeccion.social.ws.app.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;


import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProyectoControllerTest {

    @Mock
    ProyectoService proyectoService;

    @InjectMocks
    ProyectoController proyectoController;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(proyectoController).build();
    }

    @Test
    void getOne() throws Exception {
        Proyecto proyecto = new Proyecto();
        String name = "project 64";
        proyecto.setId(12);
        proyecto.setNombre(name);
        Mockito.when(proyectoService.findById(Mockito.anyInt())).thenReturn(proyecto);
        mvc.perform(get("/proyectos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.estudiante.carnet", is(carnet)));
                .andExpect(jsonPath("$.nombre", is(name)));



    }

    @Test
    void getRange() throws Exception {
        Pageable request = PageRequest.of(1, 10);
        List<Proyecto> payload = List.of(new Proyecto(), new Proyecto());
        Mockito.when(proyectoService.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(payload);
        mvc.perform(get("/proyectos/")
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
}