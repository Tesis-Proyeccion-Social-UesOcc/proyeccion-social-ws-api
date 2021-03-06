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
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CertificadoServiceTest {

    @Mock
    CertificadoRepository certificadoRepository;

    @Mock
    Storage storage;

    @Mock
    ProyectoRepository proyectoRepository;

    CertificadoService service;
    public static final int PAGE = 5;
    public static final int SIZE = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CertificadoServiceImpl(certificadoRepository, proyectoRepository, CertificadoMapper.INSTANCE, storage);
    }

    @Test
    void save() {
        int certificadoId = 101, proyectoId=200;
        String proyecto = "TEST";
        ArgumentCaptor<Certificado> certificadoArgumentCaptor = ArgumentCaptor.forClass(Certificado.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        Proyecto proyecto1 = new Proyecto(proyecto, null, false, null, null, null, null);
        proyecto1.setId(102);
        Optional<Proyecto> proyectoOptional = Optional.of(proyecto1);
        Certificado certificado = new Certificado();
        certificado.setProyecto(proyecto1);
        certificado.setId(certificadoId);
        CertificadoCreationDTO certificadoCreationDTO = new CertificadoCreationDTO(proyectoId, "http://gcp.com");

        Mockito.when(proyectoRepository.findById(proyectoId)).thenReturn(proyectoOptional);
        Mockito.when(certificadoRepository.save(Mockito.any(Certificado.class))).thenReturn(certificado);

        Optional<CertificadoCreationDTO.CertificadoDTO> certificadoDTO = service.save(certificadoCreationDTO);

        Mockito.verify(this.certificadoRepository, Mockito.times(1)).save(certificadoArgumentCaptor.capture());
        Mockito.verify(this.proyectoRepository, Mockito.times(1)).findById(integerArgumentCaptor.capture());

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

        Mockito.when(proyectoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Proyecto()));
        Mockito.when(certificadoRepository.save(Mockito.any())).thenReturn(new Certificado());
        assertThrows(IllegalArgumentException.class, () -> service.save(certificadoCreationDTO));
    }

    @Test
    void findAll() {
        String projectName1 = "Test1", projectName2 = "Test2";
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        var proyecto1 = new Proyecto(projectName1, null, false, null, null, null, null);

        var proyecto2 = new Proyecto(projectName2, null, false, null, null, null, null);

        Certificado certificado1 = new Certificado();
        certificado1.setProyecto(proyecto1);
        Certificado certificado2 = new Certificado();
        certificado2.setProyecto(proyecto2);

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

        Mockito.when(this.certificadoRepository.findAllByProyecto_ProyectoEstudianteSet_Estudiante_Carnet(
                Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(certificadoPage);

        var result = this.service.findAllByCarnet(PAGE, SIZE, carnet);

        Mockito.verify(this.certificadoRepository, Mockito.times(1))
                .findAllByProyecto_ProyectoEstudianteSet_Estudiante_Carnet(carnetArgumentCaptor.capture(), pageableArgumentCaptor.capture());
        assertNotNull(pageableArgumentCaptor.getValue());
        assertNotNull(carnetArgumentCaptor.getValue());
        assertEquals(PAGE, pageableArgumentCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableArgumentCaptor.getValue().getPageSize());
        // there is no way to assert student carnet with the given one because its not part of CertificadoDTO
        assertEquals(carnet, carnetArgumentCaptor.getValue());
        assertEquals(2, result.getContent().size());
    }
}