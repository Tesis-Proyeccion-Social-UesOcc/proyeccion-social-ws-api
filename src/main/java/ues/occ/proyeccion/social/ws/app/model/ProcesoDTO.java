package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;

import java.io.Serializable;

import java.time.LocalDate;

@Data
public class ProcesoDTO implements Serializable {
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
