package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;

public interface ProyectoService {
    Proyecto findById(int id);
    Page<ProyectoCreationDTO.ProyectoDTO> findAll(int page, int size);
    Page<ProyectoCreationDTO.ProyectoDTO> findAllByStatus(int page, int size, int statusId);
    Page<ProyectoCreationDTO.ProyectoDTO> findAllPending(int page, int size);
    Page<ProyectoCreationDTO.ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status);
    ProyectoCreationDTO save(String carnet, ProyectoCreationDTO proyecto);
}

