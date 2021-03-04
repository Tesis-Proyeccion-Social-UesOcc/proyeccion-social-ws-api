package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.dao.Departamento;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.dao.PersonalEncargado;
import ues.occ.proyeccion.social.ws.app.dao.TipoPersonal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// activate test profile to test it ===> spring.profiles.active=test
@DataJpaTest
@Profile("test")
class PersonalRepositoryIT {

    @Autowired PersonalRepository personalRepository;

    @Autowired PersonalEncargadoRepository personalEncargadoRepository;

    @Autowired DepartamentoRepository departamentoRepository;

    @Autowired TipoPersonalRepository tipoPersonalRepository;

    @Test
    void getPersonalByDepartmentName() {
         var encargado = new PersonalEncargado(1, "1-5", "ues");
         var tipo = new TipoPersonal(1, "docente", "something");
         var departamento = new Departamento(1, "Quimica");


         var personal = new Personal(1, "Jose1", "Salazar", "some@gmail.com", departamento, tipo, encargado);
         var personal2 = new Personal(2, "Jose2", "Salazar", "some2@gmail.com", departamento, tipo);
         tipoPersonalRepository.save(tipo);
         departamentoRepository.save(departamento);
         personalRepository.saveAll(List.of(personal2, personal));
//       personalEncargadoRepository.save(encargado);
         var result = personalRepository
             .getPersonalByDepartmentName("quimica")
             .get();
         assertEquals(2, personalRepository.count());
         assertNotNull(result);
         assertEquals(result.getNombre(), personal.getNombre());
         assertEquals(result.getApellido(), personal.getApellido());
         assertEquals(result.getEmail(), personal.getEmail());
         assertEquals(result.getPersonalEncargado().getHorario(), encargado.getHorario());
         assertEquals(result.getPersonalEncargado().getUbicacion(), encargado.getUbicacion());
    }

    @Test
    void findByTipoPersonal_Id(){
        var encargado = new PersonalEncargado();
        var tipo = new TipoPersonal(3, "docente", "something");
        var departamento = new Departamento(1, "Quimica");
        encargado.setId(1);
        encargado.setHorario("1-5");
        encargado.setUbicacion("ues");
        var personal = new Personal(1, "Bruce", "Wayne", "some@gmail.com", departamento, tipo, encargado);
        tipoPersonalRepository.save(tipo);
        departamentoRepository.save(departamento);
        personalRepository.save(personal);
        personalEncargadoRepository.save(encargado);
        var result = personalRepository
                .findByTipoPersonal_Id(3)
                .get();
        assertEquals(1, personalRepository.count());
        assertNotNull(result);
        assertEquals(result.getNombre(), personal.getNombre());
        assertEquals(result.getApellido(), personal.getApellido());
        assertEquals(result.getEmail(), personal.getEmail());
        assertEquals(result.getPersonalEncargado().getHorario(), encargado.getHorario());
        assertEquals(result.getPersonalEncargado().getUbicacion(), encargado.getUbicacion());

    }
}