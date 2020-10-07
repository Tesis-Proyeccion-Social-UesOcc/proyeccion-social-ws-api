package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoServiceImplTest {

    @Mock
    private ProyectoRepository proyectoRepository;
    @InjectMocks
    private ProyectoServiceImpl proyectoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    void testFindAll() {
        List<Proyecto> data = List.of(new Proyecto(), new Proyecto());
        Pageable pageable = PageRequest.of(5, 10);
        Page<Proyecto> page = new PageImpl<>(data, pageable, data.size());
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.when((this.proyectoRepository.findAll(Mockito.any(Pageable.class)))).thenReturn(page);
        List<Proyecto> result = this.proyectoService.findAll(5, 10);
        Mockito.verify(
                this.proyectoRepository,
                Mockito.times(1)
        ).findAll(captor.capture());
        Pageable captured = captor.getValue();
        assertEquals(5, captured.getPageNumber());
        assertEquals(10, captured.getPageSize());
        assertNotNull(result);
        assertEquals(2, result.size());

    }
}