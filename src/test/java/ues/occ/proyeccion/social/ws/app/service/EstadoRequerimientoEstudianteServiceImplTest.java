package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.mappers.EstadoRequerimientoEstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;

import javax.persistence.EntityManager;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EstadoRequerimientoEstudianteServiceImplTest {

    static final int PAGE = 5;
    static final int SIZE = 10;

    @Mock
    EstadoRequerimientoEstudianteRepository repository;

    @Mock
    EntityManager entityManager;

    EstadoRequerimientoEstudianteService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new EstadoRequerimientoEstudianteServiceImpl(entityManager, repository, EstadoRequerimientoEstudianteMapper.INSTANCE);
    }

    @Test
    void findAllByCarnet() {
        EstadoRequerimientoEstudiante estadoRequerimientoEstudiante1 = new EstadoRequerimientoEstudiante();
        estadoRequerimientoEstudiante1.setAprobado(true);
        estadoRequerimientoEstudiante1.setFechaEntrega(Date.valueOf("2019-12-01"));
        EstadoRequerimientoEstudiante estadoRequerimientoEstudiante2 = new EstadoRequerimientoEstudiante();
        estadoRequerimientoEstudiante2.setAprobado(true);
        List<EstadoRequerimientoEstudiante> data = List.of(estadoRequerimientoEstudiante1, estadoRequerimientoEstudiante2);
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        Page<EstadoRequerimientoEstudiante> page = new PageImpl<>(data, pageable, data.size());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> isAprobadoCaptor = ArgumentCaptor.forClass(Boolean.class);

        Mockito.when(this.repository.findAllByEstudiante_CarnetAndAprobado(
                Mockito.anyString(), Mockito.anyBoolean(), Mockito.any(Pageable.class))).thenReturn(page);

        var result=
                this.service.findAllByCarnet(5, 10, "zh15002", true);

        Mockito.verify(this.repository, Mockito.times(1)).findAllByEstudiante_CarnetAndAprobado(
                carnetCaptor.capture(), isAprobadoCaptor.capture(), pageableCaptor.capture()
        );

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(isAprobadoCaptor.getValue());
        assertEquals(PAGE, pageableCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableCaptor.getValue().getPageSize());
        assertEquals("zh15002", carnetCaptor.getValue());
        assertTrue(result.getContent().get(0).isAprobado());
        assertEquals(Date.valueOf("2019-12-01"), result.getContent().get(0).getFechaEntrega());
        assertTrue(result.getContent().get(1).isAprobado());
    }

    @Test
    void save() {
        String carnet = "zh15002";
        int requerimientoId = 100;
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);

        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setcantidadCopias(3);

        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> requerimientoIDCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<EstadoRequerimientoEstudiante> pojoCaptor = ArgumentCaptor.forClass(EstadoRequerimientoEstudiante.class);

        EstadoRequerimientoEstudiante estadoRequerimientoEstudiante = new EstadoRequerimientoEstudiante(
                estudiante, requerimiento, true, true,
                Date.valueOf("2019-12-01"), null);

        Mockito.when(this.entityManager.getReference(ArgumentMatchers.<Class<Estudiante>>any(), Mockito.anyString())).thenReturn(estudiante);
        Mockito.when(this.entityManager.getReference(ArgumentMatchers.<Class<Requerimiento>>any(), Mockito.anyInt())).thenReturn(requerimiento);
        Mockito.when(this.repository.save(Mockito.any(EstadoRequerimientoEstudiante.class))).thenReturn(estadoRequerimientoEstudiante);

        Optional<EstadoRequerimientoEstudianteDTO> result = this.service.save(carnet, requerimientoId);

        Mockito.verify(this.repository, Mockito.times(1)).save(ArgumentMatchers.any(EstadoRequerimientoEstudiante.class));
        Mockito.verify(this.entityManager, Mockito.times(1))
                .getReference(ArgumentMatchers.eq(Estudiante.class), carnetCaptor.capture());
        Mockito.verify(this.entityManager, Mockito.times(1))
                .getReference(ArgumentMatchers.eq(Requerimiento.class), requerimientoIDCaptor.capture());

        assertTrue(result.isPresent());
        assertEquals(carnet, carnetCaptor.getValue());
        assertEquals(requerimientoId, requerimientoIDCaptor.getValue());
        assertTrue(result.get().isAprobado());
        assertEquals(Date.valueOf("2019-12-01"), result.get().getFechaEntrega());
    }

    @Test
    void whenCarnetHasInvalidLengthThenThrowsAnException(){
        String invalidCarnet = "zh";
        assertThrows(IllegalArgumentException.class, () -> this.service.save(invalidCarnet, 1));
    }
}