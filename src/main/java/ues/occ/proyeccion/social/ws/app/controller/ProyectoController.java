package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;

@RestController
@RequestMapping(value = "/proyectos")
public class ProyectoController {

	@Autowired
	private ProyectoService proyectoService;
	
	@GetMapping
	public ResponseEntity<ServiceResponse> findAll(){
		return proyectoService.findAll(); 
	}
	
	@PostMapping
	public ResponseEntity<ServiceResponse> create(@RequestBody Proyecto proyecto){
		return proyectoService.create(proyecto);
	}
}
