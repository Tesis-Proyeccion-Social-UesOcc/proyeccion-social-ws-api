package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
    public class EstadoRequerimientoEstudiantePK implements Serializable {
    @Column(name = "id_requerimiento")
    private Integer idRequerimiento;

    @Column(name = "id_proyecto_estudiante")
    private Integer idProyectoEstudiante;

    public EstadoRequerimientoEstudiantePK(Integer idRequerimiento, Integer idProyectoEstudiante) {
        this.idRequerimiento = idRequerimiento;
        this.idProyectoEstudiante = idProyectoEstudiante;
    }

    public EstadoRequerimientoEstudiantePK() {

    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Integer getIdProyectoEstudiante() {
        return idProyectoEstudiante;
    }

    public void setIdProyectoEstudiante(Integer idProyectoEstudiante) {
        this.idProyectoEstudiante = idProyectoEstudiante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoRequerimientoEstudiantePK that = (EstadoRequerimientoEstudiantePK) o;
        return Objects.equals(idRequerimiento, that.idRequerimiento) && Objects.equals(idProyectoEstudiante, that.idProyectoEstudiante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRequerimiento, idProyectoEstudiante);
    }
}
