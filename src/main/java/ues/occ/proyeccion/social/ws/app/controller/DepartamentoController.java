package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.DepartamentoService;

@RestController
@RequestMapping(value = "/departamento")
public class DepartamentoController {

	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping
	public ResponseEntity<ServiceResponse> findAll(){
		return departamentoService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ServiceResponse> findById(@PathVariable("id") int id){
		return departamentoService.findById(id);
	}
	
}
