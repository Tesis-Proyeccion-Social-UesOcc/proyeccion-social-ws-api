package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface ProyectoEstudianteService {

	public abstract ResponseEntity<ServiceResponse> crearProyecto();
}
