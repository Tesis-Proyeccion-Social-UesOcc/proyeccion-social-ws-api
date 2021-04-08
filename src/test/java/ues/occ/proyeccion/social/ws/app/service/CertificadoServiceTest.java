package ues.occ.proyeccion.social.ws.app.service;

import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CertificadoServiceTest {

    @Mock
    CertificadoRepository certificadoRepository;

    @Mock
    Storage storage;

    @Mock
    ProyectoEstudianteRepository proyectoEstudianteRepository;

    CertificadoService service;
    public static final int PAGE = 5;
    public static final int SIZE = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CertificadoServiceImpl(storage, certificadoRepository, proyectoEstudianteRepository, CertificadoMapper.INSTANCE);
    }

    @Test
    void save() {
        int proyectoEstudianteId=200;
        String proyecto = "TEST";
        ArgumentCaptor<Certificado> certificadoArgumentCaptor = ArgumentCaptor.forClass(Certificado.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        var estudiante = new Estudiante("zh15002", 150, false);
        Proyecto proyecto1 = new Proyecto(proyecto, null, false, null, null, null, null);
        proyecto1.setId(102);
        var proyectoEstudiante = new ProyectoEstudiante(estudiante, proyecto1, true);
        proyectoEstudiante.setId(proyectoEstudianteId);
        Optional<ProyectoEstudiante> proyectoEstudianteOptional = Optional.of(proyectoEstudiante);
        Certificado certificado = new Certificado();
        certificado.setProyectoEstudiante(proyectoEstudiante);
        certificado.setId(proyectoEstudianteId);
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(proyectoEstudianteId, "http://gcp.com");

        Mockito.when(proyectoEstudianteRepository.findById(proyectoEstudianteId)).thenReturn(proyectoEstudianteOptional);
        Mockito.when(certificadoRepository.save(Mockito.any(Certificado.class))).thenReturn(certificado);

        var certificadoDTO = service.save(certificadoCreationDTO);

        Mockito.verify(this.certificadoRepository, Mockito.times(1)).save(certificadoArgumentCaptor.capture());
        Mockito.verify(this.proyectoEstudianteRepository, Mockito.times(1)).findById(integerArgumentCaptor.capture());

        assertTrue(certificadoDTO.isPresent());
        CertificadoCreationDTO.CertificadoDTO certificadoResult = certificadoDTO.get();

        assertEquals(proyectoEstudianteId, certificadoResult.getProyectoEstudianteId());
        assertEquals(proyectoEstudianteId, integerArgumentCaptor.getValue());
        assertEquals(proyectoEstudianteId, certificadoArgumentCaptor.getValue().getId());
        assertEquals(certificadoCreationDTO.getUri(), certificadoArgumentCaptor.getValue().getUri());
    }

    @Test
    void saveThrowsExceptionOnMalformedUrl(){

        int proyectoId=200;

        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(proyectoId, "gcp.com");

        Mockito.when(proyectoEstudianteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new ProyectoEstudiante()));
        Mockito.when(certificadoRepository.save(Mockito.any())).thenReturn(new Certificado());
        assertThrows(IllegalArgumentException.class, () -> service.save(certificadoCreationDTO));
    }

    @Test
    void findAll() {
        String projectName1 = "Test1", projectName2 = "Test2";
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        var estudiante1 = new Estudiante("zh15002", 150, false);
        var estudiante2 = new Estudiante("ab12345", 150, false);

        var proyecto1 = new Proyecto(projectName1, null, false, null, null, null, null);

        var proyecto2 = new Proyecto(projectName2, null, false, null, null, null, null);

        var proyectoEstudiante1 = new ProyectoEstudiante(estudiante1, proyecto1, true);
        var proyectoEstudiante2 = new ProyectoEstudiante(estudiante2, proyecto2, true);

        Certificado certificado1 = new Certificado();
        certificado1.setProyectoEstudiante(proyectoEstudiante1);
        Certificado certificado2 = new Certificado();
        certificado2.setProyectoEstudiante(proyectoEstudiante2);

        List<Certificado> data = List.of(certificado1, certificado2);
        Page<Certificado> certificadoPage = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        Mockito.when(this.certificadoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(certificadoPage);
        var result = this.service.findAll(PAGE, SIZE);
        Mockito.verify(this.certificadoRepository, Mockito.times(1)).findAll(pageableArgumentCaptor.capture());

        assertNotNull(pageableArgumentCaptor.getValue());
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        assertEquals(2, result.getContent().size());
        assertEquals(projectName1, result.getContent().get(0).getProyecto());
        assertEquals(projectName2, result.getContent().get(1).getProyecto());

    }

    @Test
    void findAllByCarnet() {
        String carnet = "zh15002";
        String projectName1 = "Test1", projectName2 = "Test2";
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Certificado certificado1 = new Certificado();
        Certificado certificado2 = new Certificado();

        List<Certificado> data = List.of(certificado1, certificado2);
        Page<Certificado> certificadoPage = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<String> carnetArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.certificadoRepository.findAllByProyectoEstudiante_Estudiante_Carnet(
                Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(certificadoPage);

        var result = this.service.findAllByCarnet(PAGE, SIZE, carnet);

        Mockito.verify(this.certificadoRepository, Mockito.times(1))
                .findAllByProyectoEstudiante_Estudiante_Carnet(carnetArgumentCaptor.capture(), pageableArgumentCaptor.capture());
        assertNotNull(pageableArgumentCaptor.getValue());
        assertNotNull(carnetArgumentCaptor.getValue());
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        // there is no way to assert student carnet with the given one because its not part of CertificadoDTO
        assertEquals(carnet, carnetArgumentCaptor.getValue());
        assertEquals(2, result.getContent().size());
    }

    @Test
    void getCertificate(){
        var projectName = "MyProject";
        var uri = "www.google.com";
        var estudiante = new Estudiante();
        var proyecto = new Proyecto(1, projectName, true, LocalDateTime.now());
        var certificado = new Certificado(10, uri, LocalDateTime.now(), new ProyectoEstudiante(estudiante, proyecto, true));

        Mockito.when(this.certificadoRepository
                .findByProyectoEstudiante_Estudiante_CarnetIgnoreCaseAndProyectoEstudiante_Proyecto_NombreIgnoreCase(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(certificado));

        var result = this.service.getCertificate("zh15002", projectName);

        assertEquals(10, result.getProyectoEstudianteId());
        assertEquals(projectName, result.getProyecto());
        assertEquals(uri, result.getUri());
        assertNotNull(result.getFechaExpedicion());
    }

    @Test
    void whenEmptyOptionalIsReturnedThanAnExceptionIsRaised(){
        Mockito.when(this.certificadoRepository
                .findByProyectoEstudiante_Estudiante_CarnetIgnoreCaseAndProyectoEstudiante_Proyecto_NombreIgnoreCase(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.service.getCertificate("zh15002", "SomeProjectName"));
    }
}