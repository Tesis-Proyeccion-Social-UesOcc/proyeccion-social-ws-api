package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;

import java.util.Set;

@Mapper
public interface EstudianteMapper {
    EstudianteMapper INSTANCE = Mappers.getMapper(EstudianteMapper.class);
    EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante);
    Set<EstudianteDTO> ToEstudianteList(Set<ProyectoEstudiante> proyectoEstudiantes, @Context CycleUtil cycleUtil);
    @Mapping(source = "proyectoEstudiante.estudiante", target = ".")
    EstudianteDTO proyectoEstudianteToEstudianteDTO(ProyectoEstudiante proyectoEstudiante, @Context  CycleUtil cycleUtil);
 }

