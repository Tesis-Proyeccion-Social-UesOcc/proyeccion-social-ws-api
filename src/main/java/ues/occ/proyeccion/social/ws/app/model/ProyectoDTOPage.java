package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProyectoDTOPage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page<ProyectoCreationDTO.ProyectoDTO> proyectos;
}
