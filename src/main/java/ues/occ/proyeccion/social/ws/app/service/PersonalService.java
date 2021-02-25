package ues.occ.proyeccion.social.ws.app.service;
import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface PersonalService {

	ResponseEntity<ServiceResponse> findAll();

	ResponseEntity<ServiceResponse> findByTipoPersonalId(int idTipoPersonal);
	
	ResponseEntity<ServiceResponse> findByDepartamentoId(int idDepartamento);
	
	ResponseEntity<ServiceResponse> findByNombre(String nombre, String apellido);
}
