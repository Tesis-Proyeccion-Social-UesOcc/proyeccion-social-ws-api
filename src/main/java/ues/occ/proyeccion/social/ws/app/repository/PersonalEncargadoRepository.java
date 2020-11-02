package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ues.occ.proyeccion.social.ws.app.dao.PersonalEncargado;

@Repository
public interface PersonalEncargadoRepository extends CrudRepository<PersonalEncargado, Integer>{

}
