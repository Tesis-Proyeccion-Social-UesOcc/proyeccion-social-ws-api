package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiantePK;

public interface EstadoRequerimientoEstudianteRepository
        extends PagingAndSortingRepository<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudiantePK> {
    Page<EstadoRequerimientoEstudiante> findAllByProyectoEstudiante_Estudiante_CarnetAndAprobado(String carnet, boolean aprobado, Pageable pageable);

    List<EstadoRequerimientoEstudiante> findAll();

    Page<EstadoRequerimientoEstudiante> findAllByProyectoEstudiante_Estudiante_Carnet(String carnet, Pageable requerimientoEstudiantePageable);

    @Query("SELECT c FROM EstadoRequerimientoEstudiante c inner join c.proyectoEstudiante pe inner join pe.estudiante e " +
            "where e.carnet like %:carnet%")
    List<EstadoRequerimientoEstudiante> findByCarnet(String carnet);

    EstadoRequerimientoEstudiante findByProyectoEstudiante_IdAndEntregadoAndRequerimientoId(Integer id, boolean entregado, int idRequerimiento);
}
