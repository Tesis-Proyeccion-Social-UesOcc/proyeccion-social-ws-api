package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.TipoPersonal;

@Repository	
public interface TipoPersonalRepository extends CrudRepository<TipoPersonal, Integer> {

}
