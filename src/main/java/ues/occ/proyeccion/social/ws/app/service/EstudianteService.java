package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

public interface EstudianteService {
    PageDtoWrapper<Estudiante, EstudianteDTO> findAllByServicio(int page, int size, boolean isComplete);
    EstudianteDTO findByCarnet(String carnet);
    
	ResponseEntity<ServiceResponse> getRequirementStatusByCardId(String carnet);
}
