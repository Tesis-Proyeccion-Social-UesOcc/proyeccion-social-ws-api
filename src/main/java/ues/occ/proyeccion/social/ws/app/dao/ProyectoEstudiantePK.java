package ues.occ.proyeccion.social.ws.app.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProyectoEstudiantePK implements Serializable {

    @Column(name = "id_proyecto")
    private Integer projectId;
    @Column(name = "carnet")
    private String studentId;

    public ProyectoEstudiantePK() {
    }

    public ProyectoEstudiantePK(Integer projectId, String studentId) {
        this.projectId = projectId;
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoEstudiantePK that = (ProyectoEstudiantePK) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, studentId);
    }
}
