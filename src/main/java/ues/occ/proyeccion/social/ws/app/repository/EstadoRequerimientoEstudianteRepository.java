package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiantePK;

public interface EstadoRequerimientoEstudianteRepository
        extends PagingAndSortingRepository<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudiantePK> {
    Page<EstadoRequerimientoEstudiante> findAllByEstudiante_CarnetAndAprobado(String carnet, boolean aprobado, Pageable pageable);
}
