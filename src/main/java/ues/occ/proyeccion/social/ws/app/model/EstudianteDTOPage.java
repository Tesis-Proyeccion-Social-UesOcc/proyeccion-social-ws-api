package ues.occ.proyeccion.social.ws.app.model;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstudianteDTOPage {
    private Page<EstudianteDTO> estudiantes;
}
