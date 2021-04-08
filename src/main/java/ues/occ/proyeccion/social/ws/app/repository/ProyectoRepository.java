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
import java.util.Optional;

// This repository extends CrudRepository
@Repository
public interface ProyectoRepository extends PagingAndSortingRepository<Proyecto, Integer> {
  
    Page<Proyecto> findAllByStatus_Id(int statusId, Pageable pageable);
    Page<Proyecto> findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(int status, String carnet, Pageable pageable);
	Optional<Proyecto> findByProyectoEstudianteSet_Estudiante_CarnetIgnoreCaseAndNombreIgnoreCase(String carnet, String projectName);
    List<Proyecto> findAllByProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(String carnet);
}
