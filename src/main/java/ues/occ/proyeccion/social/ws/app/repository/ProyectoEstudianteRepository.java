package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;

import java.util.Optional;

@Repository
public interface ProyectoEstudianteRepository extends PagingAndSortingRepository<ProyectoEstudiante, Integer> {
    Optional<ProyectoEstudiante> findByProyecto_Id(int proyectoId);
}
