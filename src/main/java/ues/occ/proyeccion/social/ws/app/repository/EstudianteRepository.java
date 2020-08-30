package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;

@Repository
public interface EstudianteRepository extends CrudRepository<Estudiante, String>{

}
