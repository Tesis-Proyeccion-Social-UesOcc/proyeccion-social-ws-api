package ues.occ.proyeccion.social.ws.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;

public interface EstadoRequerimientoEstudianteService {
    Optional<EstadoRequerimientoEstudianteDTO> save(String carnet, int requerimientoId);
    Page<EstadoRequerimientoEstudianteDTO> findAllByCarnet(int page, int size, String carnet, boolean aprobado);
}
