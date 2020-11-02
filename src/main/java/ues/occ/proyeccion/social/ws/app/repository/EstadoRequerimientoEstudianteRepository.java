package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiantePK;

public interface EstadoRequerimientoEstudianteRepository
        extends CrudRepository<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudiantePK> {
}
