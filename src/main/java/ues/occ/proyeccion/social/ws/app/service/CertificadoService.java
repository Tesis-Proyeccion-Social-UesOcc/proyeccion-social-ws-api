package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface CertificadoService {

	ResponseEntity<ServiceResponse> crearCertificado(Certificado certificado);
	
}
