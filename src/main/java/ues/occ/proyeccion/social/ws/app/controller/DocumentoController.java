package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.dto.DocumentoRequest;
import ues.occ.proyeccion.social.ws.app.service.DocumentoServiceImpl;

@RestController
@RequestMapping(value = "documentos")
public class DocumentoController {

	@Autowired
	private DocumentoServiceImpl documentoServiceImpl;

	@GetMapping
	public ResponseEntity<ServiceResponse> findAll() {
		return documentoServiceImpl.findAll();
	}

	@PostMapping
	public ResponseEntity<ServiceResponse> crearDocumento(@ModelAttribute DocumentoRequest model) {

		return documentoServiceImpl.crearDocumento(model);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse> findDocumentoById(@PathVariable("id") int id) {
		return documentoServiceImpl.findById(id);
	}
	
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<ServiceResponse> findDocumentoByNombre(@PathVariable("nombre") String nombre) {
		return documentoServiceImpl.findDocumentoByNombre(nombre);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResponse> deleteById(@PathVariable("id") int id) {
		return documentoServiceImpl.deleteById(id);
	} 
}
