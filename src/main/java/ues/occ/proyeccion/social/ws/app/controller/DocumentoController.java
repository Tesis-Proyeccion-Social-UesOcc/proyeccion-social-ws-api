package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
