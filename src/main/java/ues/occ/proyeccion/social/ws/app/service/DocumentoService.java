package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.DocumentoRequest;

public interface DocumentoService {

	ResponseEntity<ServiceResponse> findDocumentoByNombre(String nombre);
	ResponseEntity<ServiceResponse> findAll();
	ResponseEntity<ServiceResponse> crearDocumento(DocumentoRequest model);
	ResponseEntity<ServiceResponse> findById(int id);
	ResponseEntity<ServiceResponse> deleteById(int id);
	ResponseEntity<ServiceResponse> updateTemplate(Integer id, DocumentoRequest model);
}
