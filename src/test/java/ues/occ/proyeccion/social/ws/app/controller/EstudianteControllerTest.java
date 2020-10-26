package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteControllerTest {

    @Mock
    EstudianteService service;

    @InjectMocks
    EstudianteController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseExceptionCatcher())
                .build();
    }

    @Test
    void getOne() throws Exception{
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
    void badRequestWhenInvalidCarnetIsProvided() throws Exception{
        String carnet = "ZH15005";
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        Mockito.when(this.service.findByCarnet(Mockito.anyString())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/estudiantes/".concat(carnet)) // carnet length doesn't match
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}