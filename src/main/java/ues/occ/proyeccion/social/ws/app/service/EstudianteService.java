package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;

import java.util.List;

public interface EstudianteService {
    List<Estudiante> findAll(int page, int size);
    Estudiante findByCarnet(String carnet);
}
