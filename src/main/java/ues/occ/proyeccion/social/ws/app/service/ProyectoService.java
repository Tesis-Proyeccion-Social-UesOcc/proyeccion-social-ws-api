package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;

import java.util.List;

public interface ProyectoService {
    Proyecto findById(int id);
    List<Proyecto> findAll(int page, int size);
    List<Proyecto> findAllByStatus(int page, int size, int statusId);
    List<Proyecto> findAllPending(int page, int size);
}
