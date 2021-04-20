package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmbeddedStudentDTO extends EstudianteDTO{
    boolean active;

    public EmbeddedStudentDTO(String carnet, int horasProgreso, boolean servicioCompleto, boolean active) {
        super(carnet, horasProgreso, servicioCompleto);
        this.active = active;
    }
}
