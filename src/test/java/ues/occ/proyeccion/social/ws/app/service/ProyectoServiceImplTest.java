package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoServiceImplTest {

    @Mock
    private ProyectoRepository proyectoRepository;
    private ProyectoServiceImpl proyectoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.proyectoService = new ProyectoServiceImpl(proyectoRepository);
    }

    @Test
    void testFindById() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(5);
        Optional<Proyecto> proyecto_ = Optional.of(proyecto);
        Mockito.when(this.proyectoRepository.findById(Mockito.anyInt()))
                .thenReturn(proyecto_);
        Proyecto resultObject = this.proyectoService.findById(1);
        assertNotNull(resultObject);
        assertEquals(resultObject.getId(), 5);
    }

    @Test
    void testFindNonexistent() {
        Mockito.when(this.proyectoRepository.findById(10))
                .thenReturn(Optional.of(new Proyecto()));
        assertThrows(ResourceNotFoundException.class, () -> this.proyectoService.findById(1));
    }
}