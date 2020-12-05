package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CertificadoServiceTest {

    @Mock
    CertificadoRepository certificadoRepository;

    @Mock
    ProyectoEstudianteRepository proyectoEstudianteRepository;

    CertificadoService service;

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
    }

    @Test
    void findAllByCarnet() {
    }
}