package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EmbeddedStudentDTO;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;

import java.util.Set;

@Mapper
public interface EstudianteMapper {
    EstudianteMapper INSTANCE = Mappers.getMapper(EstudianteMapper.class);

    @Named("id")
    default int getProyectoEstudianteId(Estudiante estudiante){
        var id = -1;
        for(var proyectoEstudiante: estudiante.getProyectoEstudianteSet()){
            if(proyectoEstudiante.getEstudiante().getCarnet().equalsIgnoreCase(estudiante.getCarnet()))
                id = proyectoEstudiante.getId();
        }
        return id;
    }

    // student services util
    @Mapping(target = "proyectoEstudianteId", source = ".", qualifiedByName = "id")
    EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante);


    @Mapping(target = "proyectoEstudianteId", source = "id")
    @Mapping(target = "servicioCompleto", source = "estudiante.servicioCompleto")
    @Mapping(target = "horasProgreso", source = "estudiante.horasProgreso")
    @Mapping(target = "carnet", source = "estudiante.carnet")
    EstudianteDTO proyectoEstudianteToDTO(ProyectoEstudiante proyectoEstudiante);

    @Mapping(target = "active", source = "proyectoEstudiante.active")
    @Mapping(target = "carnet", source = "estudiante.carnet")
    @Mapping(target = "horasProgreso", source = "estudiante.horasProgreso")
    @Mapping(target = "servicioCompleto", source = "estudiante.servicioCompleto")
    @Mapping(target = "proyectoEstudianteId", source = "estudiante", qualifiedByName = "id")
    EmbeddedStudentDTO proyectoEstudianteToEmbeddedStudentDto(ProyectoEstudiante proyectoEstudiante);
    Set<EmbeddedStudentDTO> toEmbeddedStudentList(Set<ProyectoEstudiante> proyectoEstudiantes, @Context CycleUtil cycleUtil);

 }

