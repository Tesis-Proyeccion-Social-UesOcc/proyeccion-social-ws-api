package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;

import java.util.List;

// This repository extends CrudRepository
@Repository
public interface ProyectoRepository extends PagingAndSortingRepository<Proyecto, Integer> {
    Page<Proyecto> findAllByProyectoEstudianteSet_StatusId(int statusId, Pageable pageable);
    // proyectos pendientes, es decir, no existen en proyecto_estudiante
    Page<Proyecto> findAllByProyectoEstudianteSet_Empty(Pageable pageable);
}
