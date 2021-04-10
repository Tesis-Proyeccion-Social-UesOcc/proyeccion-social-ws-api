package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.model.ProcesoDTO;
import ues.occ.proyeccion.social.ws.app.service.ProcesoService;

import java.time.LocalDate;
import java.util.List;

class ProcesoControllerTest {

    @Mock
    ProcesoService service;

    @InjectMocks
    ProcesoController controller;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAll() throws Exception{
        var dto = new ProcesoDTO(
                "entrega", "",
                LocalDate.of(2021, 2, 4),
                LocalDate.of(2021, 2, 25));

        Mockito.when(service.findAll()).thenReturn(List.of(dto));

        mvc.perform(MockMvcRequestBuilders.get("/procesos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", IsCollectionWithSize.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].nombre", CoreMatchers.is(dto.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].descripcion", CoreMatchers.is(dto.getDescripcion())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].fechaInicio", CoreMatchers.is("2021-02-04")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].fechaFin", CoreMatchers.is("2021-02-25")));
    }
}