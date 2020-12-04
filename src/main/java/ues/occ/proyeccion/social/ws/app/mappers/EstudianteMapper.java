package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;

@Mapper
public interface EstudianteMapper {
    EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante);
}
