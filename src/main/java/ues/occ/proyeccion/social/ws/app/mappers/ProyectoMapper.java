package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.ProyectoDTO;

@Mapper
public interface ProyectoMapper {
    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);
    ProyectoDTO proyectoToProyectoDTO(Proyecto proyecto);
    Proyecto proyectoDTOToProyecto(ProyectoDTO proyectoDTO);
}
