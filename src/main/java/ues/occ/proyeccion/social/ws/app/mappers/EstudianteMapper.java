package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;

@Mapper
public interface EstudianteMapper {
    public static EstudianteMapper INSTANCE = Mappers.getMapper(EstudianteMapper.class);
    EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante);
}
