package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingProjectDTO implements Serializable, ProjectMarker {
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
