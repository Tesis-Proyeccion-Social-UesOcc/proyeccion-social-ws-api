package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ProyectoDTOList implements Serializable {
    private List<ProyectoCreationDTO.ProyectoDTO> proyectos;
}
