package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.CertificadoServiceImpl;

@RestController
@RequestMapping(value = "/proyectos-estudiante")
public class ProyectoEstudianteController {

	@Autowired
	private CertificadoServiceImpl certificadoServiceImpl;
	
	@PostMapping(value = "/certificado")
	public ResponseEntity<ServiceResponse> crearCertificado(@RequestBody Certificado certificado){
		return certificadoServiceImpl.crearCertificado(certificado);
	}
}
