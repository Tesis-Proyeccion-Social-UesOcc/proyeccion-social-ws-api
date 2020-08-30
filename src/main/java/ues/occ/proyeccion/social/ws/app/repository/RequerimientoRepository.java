package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;

@Repository
public interface RequerimientoRepository extends CrudRepository<Requerimiento, Integer>{

}
