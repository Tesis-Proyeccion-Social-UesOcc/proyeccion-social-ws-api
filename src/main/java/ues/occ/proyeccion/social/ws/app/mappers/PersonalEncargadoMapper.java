package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.model.PersonalEncargadoDTO;

@Mapper
public interface PersonalEncargadoMapper {
    @Mapping(source = "departamento.nombre", target = "departamento")
    @Mapping(source = "personalEncargado.horario", target = "horario")
    @Mapping(source = "personalEncargado.ubicacion", target = "ubicacion")
    PersonalEncargadoDTO personalToEncangadoDTO(Personal personal);
}
