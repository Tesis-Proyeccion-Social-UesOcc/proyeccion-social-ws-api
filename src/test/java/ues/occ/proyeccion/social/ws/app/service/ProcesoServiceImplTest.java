package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ues.occ.proyeccion.social.ws.app.dao.Proceso;
import ues.occ.proyeccion.social.ws.app.mappers.ProcesoMapper;
import ues.occ.proyeccion.social.ws.app.model.ProcesoDTO;
import ues.occ.proyeccion.social.ws.app.repository.ProcesoRepository;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcesoServiceImplTest {

    @Mock
    ProcesoRepository repository;

    ProcesoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ProcesoServiceImpl(repository, Mappers.getMapper(ProcesoMapper.class));
    }

    @Test
    void findAll() {
        var fechaInicio = Date.valueOf("2020-01-01");
        var fechaFin = Date.valueOf("2020-12-01");
        var proceso = new Proceso(1, "Entrega ficha", "", Date.valueOf("2020-01-01"), Date.valueOf("2020-12-01"));
        Mockito.when(repository.findAll()).thenReturn(List.of(proceso));

        var result = service.findAll();
        var dto = result.get(0);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(proceso.getNombre(), dto.getNombre());
        assertEquals(proceso.getDescripcion(), dto.getDescripcion());
        assertEquals(Date.valueOf(dto.getFechaInicio()), fechaInicio);
        assertEquals(Date.valueOf(dto.getFechaFin()), fechaFin);
    }
}