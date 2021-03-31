package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleDocumentDTO {
    private String nombre;
    private boolean entregado;
    private boolean aprobado;
}
