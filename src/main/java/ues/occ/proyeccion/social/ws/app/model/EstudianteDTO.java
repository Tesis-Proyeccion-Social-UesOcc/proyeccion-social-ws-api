package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;

@Data
public class EstudianteDTO {
    private String carnet;
    private int horasProgreso;
    private boolean servicioCompleto;
}
