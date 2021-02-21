package ues.occ.proyeccion.social.ws.app.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.PersonalService;

@RequestMapping(value = "/personal")
@RestController
public class PersonalController {

	@Autowired
	private PersonalService personalService;

	@GetMapping
	public ResponseEntity<ServiceResponse> findAll(
			@RequestParam(name = "nombre", defaultValue = "") String nombre,
			@RequestParam(name ="apellido", defaultValue = "") String apellido,
			@RequestParam(name = "interno", defaultValue = "1") int interno) {

		if (!nombre.isEmpty() && !apellido.isEmpty()) {
			return personalService.findByNombre(nombre, apellido);
		} 

		return personalService.findAll(interno);
	}

	@GetMapping(value = "/findByIdTipoPersonal/{idTipoPersonal}")
	public ResponseEntity<ServiceResponse> findByTipoPersonal(@PathVariable("idTipoPersonal") int idTipoPersonal) {
		return personalService.findByTipoPersonalId(idTipoPersonal);
	}

	@GetMapping(value = "/findByIdDepartamento/{idDepartamento}")
	public ResponseEntity<ServiceResponse> findByDepartamentoId(@PathVariable("idDepartamento") int idDepartamento) {
		return personalService.findByDepartamentoId(idDepartamento);
	}


}
