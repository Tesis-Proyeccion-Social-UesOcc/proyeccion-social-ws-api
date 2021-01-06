package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;

import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;

public interface EstudianteService {
    Page<EstudianteDTO> findAllByServicio(int page, int size, boolean isComplete);
    EstudianteDTO findByCarnet(String carnet);
}
