package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

public interface ProyectoService {
    ProyectoCreationDTO.ProyectoDTO findById(int id);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAll(int page, int size);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllByStatus(int page, int size, int statusId);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllPending(int page, int size);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status);
    ProyectoCreationDTO.ProyectoDTO save(ProyectoCreationDTO proyecto);
    ProyectoCreationDTO.ProyectoDTO update(ProyectoCreationDTO proyecto, int idProyecto);
}

