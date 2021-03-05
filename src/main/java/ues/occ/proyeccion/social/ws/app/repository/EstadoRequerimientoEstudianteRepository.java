package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiantePK;

public interface EstadoRequerimientoEstudianteRepository
        extends PagingAndSortingRepository<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudiantePK> {
    Page<EstadoRequerimientoEstudiante> findAllByEstudiante_CarnetAndAprobado(String carnet, boolean aprobado, Pageable pageable);

    List<EstadoRequerimientoEstudiante> findAll();

	Page<EstadoRequerimientoEstudiante> findAllByEstudiante_Carnet(String carnet,
			Pageable requerimientoEstudiantePageable);

	@Query("SELECT c FROM EstadoRequerimientoEstudiante c WHERE c.estudiante.carnet like %:carnet%")
	List<EstadoRequerimientoEstudiante> findByCarnet(String carnet);
}
