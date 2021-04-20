package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.SimpleDocumentDTO;

import java.util.List;
import java.util.Set;

@Mapper
public interface SimpleDocumentMapper {

    @Mapping(source = "requerimiento.id", target = "idRequerimiento")
    @Mapping(source = "proyectoEstudiante.id", target = "proyectoEstudianteId")
    @Mapping(source = "requerimiento.documento.nombre", target = "nombre")
    SimpleDocumentDTO documentoToDTO(EstadoRequerimientoEstudiante data, @Context CycleUtil cycleUtil);

    Set<SimpleDocumentDTO> toSimpleDocumentDTO(List<EstadoRequerimientoEstudiante> data, @Context CycleUtil cycleUtil);
}
