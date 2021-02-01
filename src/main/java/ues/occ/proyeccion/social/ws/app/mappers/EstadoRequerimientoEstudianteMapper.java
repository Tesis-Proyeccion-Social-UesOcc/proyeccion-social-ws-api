package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;

@Mapper
public interface EstadoRequerimientoEstudianteMapper {
    public static EstadoRequerimientoEstudianteMapper INSTANCE = Mappers.getMapper(EstadoRequerimientoEstudianteMapper.class);
    // TODO: VERIFY IF ITS POSSIBLE THIS MAPPING
    EstadoRequerimientoEstudiante DTOtoEstadoRequerimientoEstudiante(EstadoRequerimientoEstudianteDTO dto);
    @Mapping(source = "fechaEntrega", target = "fechaEntrega", dateFormat = "yyyy-mm-dd")
    EstadoRequerimientoEstudianteDTO estadoRequerimientoEstudianteToDTO(EstadoRequerimientoEstudiante estadoRequerimientoEstudiante);
}
