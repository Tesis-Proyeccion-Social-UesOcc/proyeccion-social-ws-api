package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;

import java.util.List;

// This repository extends CrudRepository
@Repository
public interface ProyectoRepository extends PagingAndSortingRepository<Proyecto, Integer> {
  
    Page<Proyecto> findAllByStatus_Id(int statusId, Pageable pageable);
    Page<Proyecto> findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(int status, String carnet, Pageable pageable);

    @Query(value="SELECT p.id, p.nombre, p.duracion, p.interno, p.id_tutor, p.id_encargado_externo, p.fecha_creacion\r\n" +
			"FROM chatbot_db.proyecto p\r\n" +
			"INNER JOIN chatbot_db.proyecto_estudiante pe ON p.id = pe.id_proyecto\r\n" +
			"WHERE pe.id_status = ?1  ORDER BY p.fecha_creacion DESC;", nativeQuery = true)
	List<Proyecto> findProyectosByStatus(int idStatus);
}
