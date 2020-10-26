package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;

import java.util.List;

public interface EstudianteService {
    List<Estudiante> findAllByServicio(int page, int size, boolean isComplete);
    Estudiante findByCarnet(String carnet);
}
