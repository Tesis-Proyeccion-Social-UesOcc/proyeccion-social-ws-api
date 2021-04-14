package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ues.occ.proyeccion.social.ws.app.dao.Documento;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.model.SimpleDocumentDTO;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface SimpleDocumentMapper {

    @Named("isAprobado")
    default Boolean isAprobado(Set<Requerimiento> requerimientos){
        var requerimiento = requerimientos.iterator().next();
        var requerimientoEstudiante = requerimiento.getEstadoRequerimientoEstudiantes().iterator().next();
        return requerimientoEstudiante.isAprobado();
    }

    @Named("isEntregado")
    default Boolean isEntregado(Set<Requerimiento> requerimientos){
        var requerimiento = requerimientos.iterator().next();
        var requerimientoEstudiante = requerimiento.getEstadoRequerimientoEstudiantes().iterator().next();
        return requerimientoEstudiante.isEntregado();
    }

    @Named("fechaEntregado")
    default Date getFechaEntregado(Set<Requerimiento> requerimientos){
        var requerimiento = requerimientos.iterator().next();
        var requerimientoEstudiante = requerimiento.getEstadoRequerimientoEstudiantes().iterator().next();
        return requerimientoEstudiante.getFechaEntrega();
    }

    @Named("fechaAprobado")
    default Date getFechaAprobado(Set<Requerimiento> requerimientos){
        var requerimiento = requerimientos.iterator().next();
        var requerimientoEstudiante = requerimiento.getEstadoRequerimientoEstudiantes().iterator().next();
        return requerimientoEstudiante.getFechaAprobacion();
    }

    @Named("idProyectoEstudiante")
    default int getReqEstudiante(Set<Requerimiento> requerimientos){
        var requerimiento = requerimientos.iterator().next();
        var requerimientoEstudiante = requerimiento.getEstadoRequerimientoEstudiantes().iterator().next();
        return requerimientoEstudiante.getId().getIdProyectoEstudiante();
    }

    @Named("idRequerimiento")
    default int getId(Set<Requerimiento> requerimientos){
        var requerimiento = requerimientos.iterator().next();
        return requerimiento.getId();
    }

    @Mapping(source = "requerimientos", target = "aprobado", qualifiedByName = "isAprobado")
    @Mapping(source = "requerimientos", target = "entregado", qualifiedByName = "isEntregado")
    @Mapping(source = "requerimientos", target = "fechaAprobacion", qualifiedByName = "fechaAprobado")
    @Mapping(source = "requerimientos", target = "fechaEntrega", qualifiedByName = "fechaEntregado")
    @Mapping(source = "requerimientos", target = "idRequerimiento", qualifiedByName = "idRequerimiento")
    @Mapping(source = "requerimientos", target = "proyectoEstudianteId", qualifiedByName = "idProyectoEstudiante")
    SimpleDocumentDTO documentoToDTO(Documento documento, @Context CycleUtil cycleUtil);

    Set<SimpleDocumentDTO> toSimpleDocumentDTO(List<Documento> documentos, @Context CycleUtil cycleUtil);
}
