package ues.occ.proyeccion.social.ws.app.service;
import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface PersonalService {

	public abstract ResponseEntity<ServiceResponse> findAll();

	public abstract ResponseEntity<ServiceResponse> findByTipoPersonalId(int idTipoPersonal);
	
	public abstract ResponseEntity<ServiceResponse> findByDepartamentoId(int idDepartamento);
	
	public abstract ResponseEntity<ServiceResponse> findByNombre(String nombre, String apellido);
}
