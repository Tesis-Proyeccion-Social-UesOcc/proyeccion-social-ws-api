package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PendingProjectDTO {
    private Integer id;
    private String nombre;
    private Integer duracion;
    private boolean interno;
    private String personal;
    private Set<EstudianteDTO> estudiantes;
    private Set<SimpleDocumentDTO> documentos;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String status;
}
