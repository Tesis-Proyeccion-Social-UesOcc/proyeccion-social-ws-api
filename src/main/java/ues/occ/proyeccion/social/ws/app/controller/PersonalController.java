package ues.occ.proyeccion.social.ws.app.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.PersonalEncargadoDTO;
import ues.occ.proyeccion.social.ws.app.service.PersonalService;

import java.util.Optional;

@RequestMapping(value = "/personal")
@RestController
public class PersonalController {

	private final PersonalService personalService;

	public PersonalController(PersonalService personalService) {
		this.personalService = personalService;
	}

	@GetMapping
	public ResponseEntity<ServiceResponse> findAll(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido) {

		if (nombre != null || apellido != null) {
			return personalService.findByNombre(nombre, apellido);
		}

		return personalService.findAll();
	}

	@GetMapping(value = "/findByIdTipoPersonal/{idTipoPersonal}")
	public ResponseEntity<ServiceResponse> findByTipoPersonal(@PathVariable("idTipoPersonal") int idTipoPersonal) {
		return personalService.findByTipoPersonalId(idTipoPersonal);
	}

	@GetMapping(value = "/findByIdDepartamento/{idDepartamento}")
	public ResponseEntity<ServiceResponse> findByDepartamentoId(@PathVariable("idDepartamento") int idDepartamento) {
		return personalService.findByDepartamentoId(idDepartamento);
	}

	@GetMapping("/encargado")
	public PersonalEncargadoDTO findByDepartmentName(@RequestParam(value = "department", required = true) String department){
		return this.personalService.findByDepartmentName(department);
	}

}
