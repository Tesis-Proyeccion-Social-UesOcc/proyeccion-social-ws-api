package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;

import java.util.Set;

@Mapper(uses = {EstudianteMapper.class})
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
    default Set<EstudianteDTO> getEstudiantes(Set<ProyectoEstudiante> proyectoEstudiantes){
        return MAPPER.ToEstudianteList(proyectoEstudiantes, new CycleUtil());
    }

    @Mapping(source = "proyecto", target = "personal", qualifiedByName = "nombreChecker")
    @Mapping(source = "proyecto.proyectoEstudianteSet", target = "estudiantes", qualifiedByName = "estudiantesBuilder")
    ProyectoCreationDTO.ProyectoDTO proyectoToProyectoDTO(Proyecto proyecto, @Context CycleUtil cycleUtil);

    @Mapping(source = "proyecto", target = "personal", qualifiedByName = "idChecker")
    ProyectoCreationDTO proyectoToProyectoCreationDTO(Proyecto proyecto);

    @Mapping(target = "id", ignore = true)
    Proyecto proyectoCreationDTOToProyecto(ProyectoCreationDTO proyectoCreationDTO);
}
