package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;

public interface CertificadoService extends StorageService{

	ResponseEntity<ServiceResponse> crearCertificado(int id, MultipartFile file,
			RedirectAttributes redirectAttributes);
	
}
