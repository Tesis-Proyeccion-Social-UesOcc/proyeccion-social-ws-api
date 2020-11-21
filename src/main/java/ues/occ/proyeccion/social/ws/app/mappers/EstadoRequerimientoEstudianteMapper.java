package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;

@Mapper
public interface EstadoRequerimientoEstudianteMapper {
    EstadoRequerimientoEstudiante DTOtoEstadoRequerimientoEstudiante(EstadoRequerimientoEstudianteDTO dto);
    EstadoRequerimientoEstudianteDTO estadoRequerimientoEstudianteToDTO(EstadoRequerimientoEstudiante estadoRequerimientoEstudiante);
}
