package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
public class ProyectoCreationDTO implements Serializable {

    private String nombre;
    private Integer duracion;
    private boolean interno;
    private int personal;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyectoDTO implements Serializable{
        private String nombre;
        private Integer duracion;
        private boolean interno;
        private String personal;
    }
}


