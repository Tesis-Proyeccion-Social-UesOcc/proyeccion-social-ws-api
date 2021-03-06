package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.DocumentoRequest;
import ues.occ.proyeccion.social.ws.app.service.DocumentoService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "plantillas")
public class DocumentoPlantillaController {

	private final DocumentoService documentService;

	public DocumentoPlantillaController(DocumentoService documentService) {
		this.documentService = documentService;
	}

	@GetMapping
	public ResponseEntity<ServiceResponse> findAll() {
		return documentService.findAll();
	}

	@PostMapping
	public ResponseEntity<ServiceResponse> crearDocumento(@ModelAttribute DocumentoRequest model) {

		return documentService.crearDocumento(model);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse> findDocumentoById(@PathVariable int id) {
		return documentService.findById(id);
	}
	
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<ServiceResponse> findDocumentoByNombre(@PathVariable String nombre) {
		/*decode required due to double encoding in the document name (part of the uri) provided by the webhook
		* i.e. a document with original name as "form for service", will be encoded resulting in "form%20for%20service"
		* which will also be encoded here, resulting in "form%2520for%2520", where %25 is the encoding of the character "%".
		**/
		var decodedDocName = URLDecoder.decode(nombre, StandardCharsets.UTF_8);
		return documentService.findDocumentoByNombre(decodedDocName);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResponse> deleteById(@PathVariable int id) {
		return documentService.deleteById(id);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ServiceResponse> updateById(@PathVariable Integer id,
			@ModelAttribute DocumentoRequest request) {
		return documentService.updateTemplate(id, request);
	}
}
