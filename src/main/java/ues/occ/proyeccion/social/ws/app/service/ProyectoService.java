package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;

import java.util.List;

public interface ProyectoService {
    Proyecto findById(int id);
    List<Proyecto> findAll(int page, int size);
    List<Proyecto> findAllByStatus(int page, int size, int statusId);
    List<Proyecto> findAllPending(int page, int size);
}
