package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Pageable;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;

import java.util.List;

public interface ProyectoService {
    Proyecto findById(int id);
    List<Proyecto> findAll(int page, int size);
}
