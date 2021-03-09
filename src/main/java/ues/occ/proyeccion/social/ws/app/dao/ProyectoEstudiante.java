package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "proyecto_estudiante")
public class ProyectoEstudiante implements Serializable {

    @EmbeddedId
    private ProyectoEstudiantePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "carnet")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @Column(name = "activo")
    private Boolean active;

    public ProyectoEstudiante() {
    }

    public ProyectoEstudiante(Estudiante estudiante, Proyecto proyecto, Boolean active) {
        this.estudiante = estudiante;
        this.proyecto = proyecto;
        this.active = active;
        this.id = new ProyectoEstudiantePK(proyecto.getId(), estudiante.getCarnet());
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoEstudiante that = (ProyectoEstudiante) o;
        return estudiante.equals(that.estudiante) && proyecto.equals(that.proyecto) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, proyecto, active);
    }
}
