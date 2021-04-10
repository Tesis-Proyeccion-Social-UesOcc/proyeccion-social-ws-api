package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import ues.occ.proyeccion.social.ws.app.dao.Proceso;
import ues.occ.proyeccion.social.ws.app.model.ProcesoDTO;

@Mapper
public interface ProcesoMapper {
    ProcesoDTO procesoToProcesoDTO(Proceso proceso);
}
