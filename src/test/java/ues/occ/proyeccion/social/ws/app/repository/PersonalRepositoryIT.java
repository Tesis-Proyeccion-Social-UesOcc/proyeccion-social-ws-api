package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ues.occ.proyeccion.social.ws.app.dao.Departamento;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.dao.PersonalEncargado;
import ues.occ.proyeccion.social.ws.app.dao.TipoPersonal;

import static org.junit.jupiter.api.Assertions.*;

// activate test profile to test it ===> spring.profiles.active=test
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Profile("test")
class PersonalRepositoryIT {

    @Autowired PersonalRepository personalRepository;

    @Autowired PersonalEncargadoRepository personalEncargadoRepository;

    @Autowired DepartamentoRepository departamentoRepository;

    @Autowired TipoPersonalRepository tipoPersonalRepository;

    @Test
    void findByPersonalEncargadoExistsAndDepartamento_NombreContainingIgnoreCase() {
     var encargado = new PersonalEncargado();
     var tipo = new TipoPersonal(1, "docente", "something");
     var departamento = new Departamento(1, "Quimica");
     encargado.setId(1);
     encargado.setHorario("1-5");
     encargado.setUbicacion("ues");
     var personal = new Personal(1, "Jose", "Salazar",true, departamento, tipo, encargado);
     tipoPersonalRepository.save(tipo);
     departamentoRepository.save(departamento);
     personalRepository.save(personal);
     personalEncargadoRepository.save(encargado);
        var result = personalRepository
             .getPersonalEncargadoByDepartmentName(null)
             .get();
        assertEquals(1, personalRepository.count());
        System.out.println(result.getNombre());
     assertNotNull(result);
     assertEquals(result.getDepartamento().getNombre(), departamento.getNombre());
     assertEquals(result.getNombre(), personal.getNombre());
     assertEquals(result.getApellido(), personal.getApellido());
     assertEquals(result.getPersonalEncargado().getHorario(), encargado.getHorario());
     assertEquals(result.getPersonalEncargado().getUbicacion(), encargado.getUbicacion());
    }
}