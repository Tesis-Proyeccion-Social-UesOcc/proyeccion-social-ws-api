package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;

@Mapper
public abstract class ProyectoMapper {
    public static ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);

    public ProyectoCreationDTO.ProyectoDTO proyectoToProyectoDTO(Proyecto proyecto){
        String personal;
        if(proyecto.isInterno())
            personal = proyecto.getTutor().getNombre();
        else
            personal = proyecto.getEncargadoExterno().getNombre();
        return ProyectoCreationDTO.ProyectoDTO.builder()
        		.id(proyecto.getId())
        		.nombre(proyecto.getNombre())
                .duracion(proyecto.getDuracion())
                .interno(proyecto.isInterno())
                .personal(personal)
                .build();
    }
    public ProyectoCreationDTO proyectoToProyectoCreationDTO(Proyecto proyecto) {
        int personal = 0;
        if(proyecto.isInterno())
            personal = proyecto.getTutor().getId();
        else
            personal = proyecto.getEncargadoExterno().getId();
        return ProyectoCreationDTO.builder()
        		.id(proyecto.getId())
        		.nombre(proyecto.getNombre())
                .duracion(proyecto.getDuracion())
                .interno(proyecto.isInterno())
                .personal(personal)
                .build();
    }
    public abstract Proyecto proyectoCreationDTOToProyecto(ProyectoCreationDTO proyectoCreationDTO);
}
