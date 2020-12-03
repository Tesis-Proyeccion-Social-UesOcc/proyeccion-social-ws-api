package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;

import java.util.List;

public interface ProyectoService {
    Proyecto findById(int id);
    List<ProyectoCreationDTO.ProyectoDTO> findAll(int page, int size);
    List<ProyectoCreationDTO.ProyectoDTO> findAllByStatus(int page, int size, int statusId);
    List<ProyectoCreationDTO.ProyectoDTO> findAllPending(int page, int size);
    List<ProyectoCreationDTO.ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status);
    ProyectoCreationDTO save(Estudiante estudiante, ProyectoCreationDTO proyecto);
}
