package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Documento;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EmbeddedStudentDTO;
import ues.occ.proyeccion.social.ws.app.model.PendingProjectDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;

import java.util.List;
import java.util.Set;

@Mapper(uses = {EstudianteMapper.class, StatusMapper.class, SimpleDocumentMapper.class})
public interface ProyectoMapper {
    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);
    EstudianteMapper MAPPER = Mappers.getMapper(EstudianteMapper.class);

    @Named("nombreChecker")
    default String getNombrePersonal(Proyecto proyecto){
        return proyecto.isInterno() ? proyecto.getTutor().getNombre() : proyecto.getEncargadoExterno().getNombre();
    }

    @Named("idChecker")
    default Integer getIdPersonal(Proyecto proyecto){
        return proyecto.isInterno() ? proyecto.getTutor().getId() : proyecto.getEncargadoExterno().getId();
    }

    @Named("estudiantesBuilder")
    default Set<EmbeddedStudentDTO> getEstudiantes(Set<ProyectoEstudiante> proyectoEstudiantes){
        return MAPPER.toEmbeddedStudentList(proyectoEstudiantes, new CycleUtil<>());
    }


    @Mapping(source = "proyecto", target = "personal", qualifiedByName = "nombreChecker")
    @Mapping(source = "proyecto.proyectoEstudianteSet", target = "estudiantes", qualifiedByName = "estudiantesBuilder")
    @Mapping(source = "proyecto.status.status", target ="status")
    ProyectoCreationDTO.ProyectoDTO proyectoToProyectoDTO(Proyecto proyecto, @Context CycleUtil cycleUtil);


    @Mapping(source = "proyecto", target = "personal", qualifiedByName = "nombreChecker")
    @Mapping(source = "proyecto.proyectoEstudianteSet", target = "estudiantes", qualifiedByName = "estudiantesBuilder")
    @Mapping(source = "proyecto.status.status", target ="status")
    @Mapping(source = "documentos", target ="documentos")
    PendingProjectDTO mapToPendingProject(Proyecto proyecto, List<Documento> documentos, @Context CycleUtil cycleUtil);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proyectoEstudianteSet", ignore = true)
    @Mapping(target = "encargadoExterno", ignore = true)
    @Mapping(target = "tutor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    Proyecto proyectoCreationDTOToProyecto(ProyectoCreationDTO proyectoCreationDTO);
}
