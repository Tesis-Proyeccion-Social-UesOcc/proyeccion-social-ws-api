package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends PagingAndSortingRepository<Estudiante, String> {
    public Optional<Estudiante> findByCarnetIgnoreCase(String carnet);
    public Page<Estudiante> findAllByServicioCompleto(Pageable pageable, boolean isComplete);
}
