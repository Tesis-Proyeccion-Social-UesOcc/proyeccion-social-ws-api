package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface DepartamentoService {

	public abstract ResponseEntity<ServiceResponse> findAll();
	
	public abstract ResponseEntity<ServiceResponse> findById();
	
	public abstract ResponseEntity<ServiceResponse> create();
	
	public abstract ResponseEntity<ServiceResponse> deleteById();
	
	public abstract ResponseEntity<ServiceResponse> updateById();
	
}
