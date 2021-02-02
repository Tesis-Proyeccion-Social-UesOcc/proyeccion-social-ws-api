package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
    private String carnet;
    private int horasProgreso;
    private boolean servicioCompleto;
}
