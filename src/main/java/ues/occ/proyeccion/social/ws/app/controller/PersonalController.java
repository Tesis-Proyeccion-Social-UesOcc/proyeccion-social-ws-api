package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.PersonalService;

@RequestMapping(value = "/personal")
@RestController
public class PersonalController {

	
	@Autowired
	private PersonalService personalService;
	
	@GetMapping(value = "/tipo-personal/{idTipoPersonal}")
	public ResponseEntity<ServiceResponse> findByTipoPersonal(@PathVariable("idTipoPersonal")int idTipoPersonal){
		return personalService.findByTipoPersonal(idTipoPersonal);
	}
	
	@GetMapping
	public ResponseEntity<ServiceResponse> findAll(){
		return personalService.findAll();	
	}
	
}
