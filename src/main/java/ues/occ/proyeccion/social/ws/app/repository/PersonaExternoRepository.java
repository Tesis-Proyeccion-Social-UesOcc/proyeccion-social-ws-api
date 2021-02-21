package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.PersonalExterno;

@Repository
public interface PersonaExternoRepository extends CrudRepository<PersonalExterno, Integer>{

	List<PersonalExterno> findAll();
}
