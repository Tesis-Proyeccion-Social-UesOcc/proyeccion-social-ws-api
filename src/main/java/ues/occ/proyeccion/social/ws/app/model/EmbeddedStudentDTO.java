package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class EmbeddedStudentDTO extends EstudianteDTO{
    boolean active;

    public EmbeddedStudentDTO(int proyectoEstudianteId, String carnet, int horasProgreso, boolean servicioCompleto, boolean active) {
        super(proyectoEstudianteId, carnet, horasProgreso, servicioCompleto);
        this.active = active;
    }
}
