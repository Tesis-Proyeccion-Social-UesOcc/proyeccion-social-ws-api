package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;

import java.util.List;

public interface EstudianteService {
    List<EstudianteDTO> findAllByServicio(int page, int size, boolean isComplete);
    EstudianteDTO findByCarnet(String carnet);
}
