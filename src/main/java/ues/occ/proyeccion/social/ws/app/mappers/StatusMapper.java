package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;

import ues.occ.proyeccion.social.ws.app.dao.Status;
import ues.occ.proyeccion.social.ws.app.model.StatusDTO;



@Mapper
public interface StatusMapper {

	StatusDTO statusToStatusDto(Status status);
}
