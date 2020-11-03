package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {
    Proyecto findById(int id);
    List<Proyecto> findAll(int page, int size);
    List<Proyecto> findAllByStatus(int page, int size, int statusId);
    List<Proyecto> findAllPending(int page, int size);
    List<Proyecto> findProyectosByEstudiante(int page, int size, String carnet);
    Proyecto save(Estudiante estudiante, Proyecto proyecto);
}
