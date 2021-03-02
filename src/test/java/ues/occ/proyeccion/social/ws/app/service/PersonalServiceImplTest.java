package ues.occ.proyeccion.social.ws.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ues.occ.proyeccion.social.ws.app.dao.Departamento;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.dao.PersonalEncargado;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.PersonalEncargadoMapper;
import ues.occ.proyeccion.social.ws.app.repository.PersonalExternoRepository;
import ues.occ.proyeccion.social.ws.app.repository.PersonalRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonalServiceImplTest {

    @Mock
    PersonalRepository repository;

    @Mock
    PersonalExternoRepository personalExternoRepository;

    PersonalService personalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personalService = new PersonalServiceImpl(repository, personalExternoRepository, Mappers.getMapper(PersonalEncargadoMapper.class));
    }

    @Test
    void findByDepartmentName() {
        var personal = new Personal();
        var encargado = new PersonalEncargado();
        var department = new Departamento();
        department.setNombre("Ingenieria");
        encargado.setHorario("5-10");
        encargado.setId(1);
        encargado.setUbicacion("ues");
        personal.setNombre("Jose");
        personal.setApellido("Salazar");
        personal.setPersonalEncargado(encargado);
        personal.setDepartamento(department);

       Mockito.when(repository
               .findByDepartamento_NombreContainingIgnoreCase(Mockito.anyString()))
               .thenReturn(Optional.of(personal));

       var result = personalService.findByDepartmentName("Anything");

       assertNotNull(result);
       assertEquals("Jose", result.getNombre());
       assertEquals("Salazar", result.getApellido());
       assertEquals("Ingenieria", result.getDepartamento());
       assertEquals("ues", result.getUbicacion());
       assertEquals("5-10", result.getHorario());
    }

    @Test
    void findByDepartmentNameWhenExceptionIsThrown() {


        Mockito.when(repository
                .findByDepartamento_NombreContainingIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personalService.findByDepartmentName("Anything"));

    }
}