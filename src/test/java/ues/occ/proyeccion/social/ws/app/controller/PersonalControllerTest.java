package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.model.PersonalEncargadoDTO;
import ues.occ.proyeccion.social.ws.app.service.PersonalService;

import static org.junit.jupiter.api.Assertions.*;

class PersonalControllerTest {

    @Mock
    PersonalService service;

    @InjectMocks
    PersonalController controller;

    MockMvc mvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseExceptionCatcher())
                .build();
    }

    @Test
    void findByDepartmentName() throws Exception{
        var dto = new PersonalEncargadoDTO("Jose", "Salazar", "ciencia", "5-10", "ues");
        Mockito.when(service.findByDepartmentName(ArgumentMatchers.nullable(String.class))).thenReturn(dto);

        var captor = ArgumentCaptor.forClass(String.class);

        mvc.perform(MockMvcRequestBuilders.get("/personal/encargado")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("area", "general"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is("Jose")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido", CoreMatchers.is("Salazar")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departamento", CoreMatchers.is("ciencia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horario", CoreMatchers.is("5-10")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ubicacion", CoreMatchers.is("ues")));

        Mockito.verify(service, Mockito.times(1)).findByDepartmentName(captor.capture());

        assertNull(captor.getValue());
    }

    @Test
    void findByDepartmentNameWithEmptyObject() throws Exception{
        Mockito.when(service.findByDepartmentName(Mockito.anyString())).thenThrow(new ResourceNotFoundException("error"));

        var captor = ArgumentCaptor.forClass(String.class);

        mvc.perform(MockMvcRequestBuilders.get("/personal/encargado")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("area", "something"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("error")));

        Mockito.verify(service, Mockito.times(1)).findByDepartmentName(captor.capture());

        assertEquals(captor.getValue(), "something");
    }
}