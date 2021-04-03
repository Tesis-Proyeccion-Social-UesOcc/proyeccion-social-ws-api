package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dto.ProyectoChangeStatusDto;
import ues.occ.proyeccion.social.ws.app.model.PendingProjectDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO.ProyectoDTO;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

public interface ProyectoService {
    ProyectoCreationDTO.ProyectoDTO findById(int id);
    ProyectoDTO findByCarnetAndProjectName(String carnet, String projectName);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAll(int page, int size);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllByStatus(int page, int size, int statusId);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllPending(int page, int size);
    PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status);
    PageDtoWrapper<Proyecto, PendingProjectDTO> findProyectosPendientesByEstudiante(int page, int size, String carnet, int status);
    ProyectoCreationDTO.ProyectoDTO save(ProyectoCreationDTO proyecto);
    ProyectoCreationDTO.ProyectoDTO update(ProyectoCreationDTO proyecto, int idProyecto);
	ProyectoDTO changeStatus(ProyectoChangeStatusDto proyecto);
}

