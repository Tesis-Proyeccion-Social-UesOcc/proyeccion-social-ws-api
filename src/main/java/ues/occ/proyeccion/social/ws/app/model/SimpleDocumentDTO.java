package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class SimpleDocumentDTO {
    private int proyectoEstudianteId;
    private int idRequerimiento;
    private String nombre;
    private boolean entregado;
    private boolean aprobado;
    private Date fechaEntrega;
    private Date fechaAprobacion;
}
