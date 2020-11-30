package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import java.time.LocalDateTime;
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
        Optional<Proyecto> proyecto = Optional.of(
                new Proyecto(
                        5, "name", 250, true, LocalDateTime.now()
                )
        );
        Mockito.when(this.proyectoRepository.findById(Mockito.anyInt()))
                .thenReturn(proyecto);
        Proyecto resultObject = this.proyectoService.findById(1);
        assertNotNull(resultObject);
        assertEquals(resultObject.getDuracion(), 250);
        assertEquals(resultObject.getId(), 5);
    }

    @Test
    void testFindUnexistent() {
        Mockito.when(this.proyectoRepository.findById(10))
                .thenReturn(Optional.of(new Proyecto()));
        assertThrows(ResourceNotFoundException.class, () -> this.proyectoService.findById(1));
    }
}