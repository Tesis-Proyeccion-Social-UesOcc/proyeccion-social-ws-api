package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;

import java.util.List;

// This repository extends CrudRepository
@Repository
public interface ProyectoRepository extends PagingAndSortingRepository<Proyecto, Integer> {
}
