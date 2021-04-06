package ues.occ.proyeccion.social.ws.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

public interface EstadoRequerimientoEstudianteService {
    Optional<EstadoRequerimientoEstudianteDTO> save(Integer idProyectoEstudiante, int requerimientoId);
    PageDtoWrapper<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO> findAllByCarnet(int page, int size, String carnet, boolean aprobado);

    PageDtoWrapper<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO> findAllByCarnet(int page, int size, String carnet);
    
}
