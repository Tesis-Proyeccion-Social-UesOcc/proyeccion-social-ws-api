package ues.occ.proyeccion.social.ws.app.service;

import org.assertj.core.internal.Arrays;
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
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CertificadoServiceTest {

    @Mock
    CertificadoRepository certificadoRepository;

    @Mock
    ProyectoEstudianteRepository proyectoEstudianteRepository;

    CertificadoService service;
    public static final int PAGE = 5;
    public static final int SIZE = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CertificadoServiceImpl(certificadoRepository, proyectoEstudianteRepository, CertificadoMapper.INSTANCE);
    }

    @Test
    void save() {
        int certificadoId = 101, proyectoId=200;
        String proyecto = "TEST";
        ArgumentCaptor<Certificado> certificadoArgumentCaptor = ArgumentCaptor.forClass(Certificado.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        ProyectoEstudiante proyectoEstudiante = new ProyectoEstudiante();
        proyectoEstudiante.setProyecto(new Proyecto(proyecto, null, false, null, null, null));
        proyectoEstudiante.setId(102);
        Optional<ProyectoEstudiante> proyectoEstudianteOptional = Optional.of(proyectoEstudiante);
        Certificado certificado = new Certificado();
        certificado.setProyectoEstudiante(proyectoEstudiante);
        certificado.setId(certificadoId);
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(proyectoId, "http://gcp.com");

        Mockito.when(proyectoEstudianteRepository.findByProyecto_Id(proyectoId)).thenReturn(proyectoEstudianteOptional);
        Mockito.when(certificadoRepository.save(Mockito.any(Certificado.class))).thenReturn(certificado);

        Optional<CertificadoCreationDTO.CertificadoDTO> certificadoDTO = service.save(certificadoCreationDTO);

        Mockito.verify(this.certificadoRepository, Mockito.times(1)).save(certificadoArgumentCaptor.capture());
        Mockito.verify(this.proyectoEstudianteRepository, Mockito.times(1)).findByProyecto_Id(integerArgumentCaptor.capture());

        assertTrue(certificadoDTO.isPresent());
        CertificadoCreationDTO.CertificadoDTO certificadoResult = certificadoDTO.get();

        assertEquals(certificadoId, certificadoResult.getId());
        assertEquals(proyectoId, integerArgumentCaptor.getValue());
        assertEquals(102, certificadoArgumentCaptor.getValue().getId());
        assertEquals(certificadoCreationDTO.getUri(), certificadoArgumentCaptor.getValue().getUri());
    }

    @Test
    void saveThrowsExceptionOnMalformedUrl(){

        int proyectoId=200;

        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(proyectoId, "gcp.com");

        Mockito.when(proyectoEstudianteRepository.findByProyecto_Id(Mockito.anyInt())).thenReturn(Optional.of(new ProyectoEstudiante()));
        Mockito.when(certificadoRepository.save(Mockito.any())).thenReturn(new Certificado());
        assertThrows(IllegalArgumentException.class, () -> service.save(certificadoCreationDTO));
    }

    @Test
    void findAll() {
        String projectName1 = "Test1", projectName2 = "Test2";
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        ProyectoEstudiante proyectoEstudiante1 = new ProyectoEstudiante();
        proyectoEstudiante1.setProyecto(new Proyecto(projectName1, null, false, null, null, null));

        ProyectoEstudiante proyectoEstudiante2 = new ProyectoEstudiante();
        proyectoEstudiante2.setProyecto(new Proyecto(projectName2, null, false, null, null, null));

        Certificado certificado1 = new Certificado();
        certificado1.setProyectoEstudiante(proyectoEstudiante1);
        Certificado certificado2 = new Certificado();
        certificado2.setProyectoEstudiante(proyectoEstudiante2);

        List<Certificado> data = List.of(certificado1, certificado2);
        Page<Certificado> certificadoPage = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        Mockito.when(this.certificadoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(certificadoPage);
        List<CertificadoCreationDTO.CertificadoDTO> result = this.service.findAll(PAGE, SIZE);
        Mockito.verify(this.certificadoRepository, Mockito.times(1)).findAll(pageableArgumentCaptor.capture());

        assertNotNull(pageableArgumentCaptor.getValue());
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        assertEquals(2, result.size());
        assertEquals(projectName1, result.get(0).getProyecto());
        assertEquals(projectName2, result.get(1).getProyecto());

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

        List<CertificadoCreationDTO.CertificadoDTO> result = this.service.findAllByCarnet(PAGE, SIZE, carnet);

        Mockito.verify(this.certificadoRepository, Mockito.times(1))
                .findAllByProyectoEstudiante_Estudiante_Carnet(carnetArgumentCaptor.capture(), pageableArgumentCaptor.capture());
        assertNotNull(pageableArgumentCaptor.getValue());
        assertNotNull(carnetArgumentCaptor.getValue());
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        // there is no way to assert student carnet with the given one because its not part of CertificadoDTO
        assertEquals(carnet, carnetArgumentCaptor.getValue());
        assertEquals(2, result.size());
    }
}