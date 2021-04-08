package ues.occ.proyeccion.social.ws.app.controller;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CertificadoControllerTest {

    @Mock
    CertificadoService certificadoService;

    @Mock
    ApplicationEventPublisher publisher;

    CertificadoController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CertificadoController(certificadoService, publisher);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestResponseExceptionCatcher()).build();
    }

    @Test
    void createCertificate() throws Exception{
        String dateStr = "2019-12-01";
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(1, "http://www.google.com");
        CertificadoCreationDTO.CertificadoDTO result = new CertificadoCreationDTO.CertificadoDTO(
                1, "Proyecto", "http://www.google.com",  LocalDateTime.of(
                LocalDate.of(2019, 12,1), LocalTime.now()));

        ArgumentCaptor<CertificadoCreationDTO> certificateCaptor = ArgumentCaptor.forClass(CertificadoCreationDTO.class);

        Mockito.when(this.certificadoService.save(Mockito.any(CertificadoCreationDTO.class))).thenReturn(Optional.of(result));

        mockMvc.perform(MockMvcRequestBuilders.post("/certificados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(certificadoCreationDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaExpedicion", CoreMatchers.is(dateStr)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri", CoreMatchers.is("http://www.google.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proyectoEstudianteId", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proyecto", CoreMatchers.is("Proyecto")));

        Mockito.verify(this.certificadoService, Mockito.times(1)).save(certificateCaptor.capture());

        assertEquals(certificadoCreationDTO, certificateCaptor.getValue());

    }

    @Test
    void testCreateCertificateWithInternalError() throws Exception{
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(1, "http://www.google.com");

        Mockito.when(this.certificadoService.save(Mockito.any(CertificadoCreationDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/certificados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(certificadoCreationDTO)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.containsString("failed")));

    }

    @Test
    void testCreateCertificateWithInvalidId() throws Exception{
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(0, "http://www.google.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/certificados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(certificadoCreationDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations[0].message", CoreMatchers.containsString("greater then")));

    }

    @Test
    void testCreateCertificateWithAllInvalidArguments() throws Exception{
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(0, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/certificados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(certificadoCreationDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations", IsCollectionWithSize.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations[*].message",
                        Matchers.containsInAnyOrder("ID must be greater then 0","You must provide the certificate url")));
    }

    @Test
    void testGetCertificates() throws Exception{
        int page = 5, size = 10;
        String dateStr1 = "2019-12-01", dateStr2 = "2019-09-01";
        String uri1 = "http://www.google.com/cert1", uri2 = "http://www.google.com/cert2";
        CertificadoCreationDTO.CertificadoDTO dto1 = new CertificadoCreationDTO.CertificadoDTO(
                1, "Proyecto1", uri1,  LocalDateTime.of(
                LocalDate.of(2019, 12,1), LocalTime.now()));
        CertificadoCreationDTO.CertificadoDTO dto2 = new CertificadoCreationDTO.CertificadoDTO(
                2, "Proyecto2", uri2,  LocalDateTime.of(
                LocalDate.of(2019, 9,1), LocalTime.now()));
        var listOfEmptyCertificado = List.of(new Certificado(), new Certificado());
        List<CertificadoCreationDTO.CertificadoDTO> data = List.of(dto1, dto2);
        var toReturn = new PageDtoWrapper<>(new PageImpl<>(listOfEmptyCertificado, PageRequest.of(page, size), listOfEmptyCertificado.size()), data);
        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> sizeCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(this.certificadoService.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(toReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificados")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "5")
                .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", IsCollectionWithSize.hasSize(data.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].proyectoEstudianteId", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].proyectoEstudianteId", CoreMatchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].proyecto", Matchers.containsInAnyOrder("Proyecto1", "Proyecto2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].uri", Matchers.containsInAnyOrder(uri1, uri2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].fechaExpedicion", Matchers.containsInAnyOrder(dateStr1, dateStr2)));


        Mockito.verify(this.certificadoService, Mockito.times(1)).findAll(pageCaptor.capture(), sizeCaptor.capture());

        assertEquals(page, pageCaptor.getValue());
        assertEquals(size, sizeCaptor.getValue());
    }

    @Test
    void getCertificateByProjectName() throws Exception{
        var carnet = "zh150021";
        var projectName = "project";
        var dto = new CertificadoCreationDTO.CertificadoDTO(10, "Test project", "www.google.com", LocalDateTime.of(2020, 12, 1, 1, 1));
        Mockito.when(this.certificadoService.getCertificate(Mockito.anyString(), Mockito.anyString())).thenReturn(dto);

        var carnetCaptor = ArgumentCaptor.forClass(String.class);
        var projectCaptor = ArgumentCaptor.forClass(String.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificados/".concat(carnet))
                .param("projectName", projectName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.proyectoEstudianteId", CoreMatchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proyecto", CoreMatchers.is("Test project")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri", CoreMatchers.is("www.google.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaExpedicion", CoreMatchers.is("2020-12-01")));

        Mockito.verify(this.certificadoService, Mockito.times(1)).getCertificate(carnetCaptor.capture(), projectCaptor.capture());

        assertEquals(carnet, carnetCaptor.getValue());
        assertEquals(projectName, projectCaptor.getValue());

    }
}