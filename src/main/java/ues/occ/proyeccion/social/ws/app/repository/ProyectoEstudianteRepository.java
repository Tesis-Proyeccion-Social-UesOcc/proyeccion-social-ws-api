package ues.occ.proyeccion.social.ws.app.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;

@Repository
public interface ProyectoEstudianteRepository extends PagingAndSortingRepository<ProyectoEstudiante, Integer> {
    Optional<ProyectoEstudiante> findByProyecto_Id(int proyectoId);
}
