package ues.occ.proyeccion.social.ws.app.service;

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
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.EstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteServiceImplTest {
    static final int PAGE = 5;
    static final int SIZE = 10;

    @Mock
    EstudianteRepository estudianteRepository;

    EstudianteService estudianteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        estudianteService = new EstudianteServiceImpl(estudianteRepository, EstudianteMapper.INSTANCE);
    }

    @Test
    void findAllByServicio() {
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setCarnet("zh1");
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setCarnet("zh2");
        List<Estudiante> data = List.of(estudiante1, estudiante2);
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        PageImpl<Estudiante> estudiantePage = new PageImpl<>(data, pageable, data.size());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Boolean> isCompleteCaptor = ArgumentCaptor.forClass(Boolean.class);

        Mockito.when(this.estudianteRepository.findAllByServicioCompleto(
                Mockito.any(Pageable.class),
                Mockito.anyBoolean())).thenReturn(estudiantePage);

//        Page<EstudianteDTO> estudianteDTOS = this.estudianteService.findAllByServicio(5, 10, true);
        Mockito.verify(this.estudianteRepository, Mockito.times(1))
                .findAllByServicioCompleto(pageableCaptor.capture(), isCompleteCaptor.capture());

//        assertNotNull(estudianteDTOS);
//        assertEquals(2, estudianteDTOS.getTotalElements());
//        assertEquals(estudianteDTOS.toList().get(0).getCarnet(), "zh1");
//        assertEquals(estudianteDTOS.toList().get(1).getCarnet(), "zh2");
        assertEquals(PAGE, pageableCaptor.getValue().getPageNumber());
        assertEquals(SIZE, pageableCaptor.getValue().getPageSize());
        assertTrue(isCompleteCaptor.getValue());
    }

    @Test
    void findByCarnet() {
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet("zh15002");
        Optional<Estudiante> toReturn = Optional.of(estudiante);

        ArgumentCaptor<String> carnetCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(this.estudianteRepository.findByCarnetIgnoreCase(Mockito.anyString())).thenReturn(toReturn);

        EstudianteDTO estudianteDTO = this.estudianteService.findByCarnet("zh15002");

        Mockito.verify(this.estudianteRepository, Mockito.times(1)).findByCarnetIgnoreCase(carnetCaptor.capture());

        assertNotNull(estudianteDTO);
        assertEquals("zh15002", estudianteDTO.getCarnet());

    }

    @Test
    void whenFindByCarnetReceivesNoValidCarnetThenThrowsException(){
        String invalidCarnet = "zh";

        assertThrows(IllegalArgumentException.class, () -> this.estudianteService.findByCarnet(invalidCarnet));
    }

    @Test
    void whenFindByCarnetReturnsEmptyOptionalThenThrowsException(){
        Mockito.when(this.estudianteRepository.findByCarnetIgnoreCase(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> this.estudianteService.findByCarnet("zh15002"));
    }
}