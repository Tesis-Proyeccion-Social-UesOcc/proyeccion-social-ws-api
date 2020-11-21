package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;

import java.util.Optional;

public interface EstadoRequerimientoEstudianteService {
    Optional<EstadoRequerimientoEstudianteDTO> save(String carnet, int requerimientoId);
}
