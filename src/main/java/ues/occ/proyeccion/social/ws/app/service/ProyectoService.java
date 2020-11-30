package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface ProyectoService {
    Proyecto findById(int id);

	public abstract ResponseEntity<ServiceResponse> findAll();
	
	public abstract ResponseEntity<ServiceResponse> create(Proyecto proyectoO);

	public abstract ResponseEntity<ServiceResponse> findProyectosByStatus(int idStatus);
}

