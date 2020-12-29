package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
    public class EstadoRequerimientoEstudiantePK implements Serializable {
    @Column(name = "id_requerimiento")
    private Integer idRequerimiento;

    @Column(name = "id_estudiante")
    private String idEstudiante;

    public EstadoRequerimientoEstudiantePK(Integer idRequerimiento, String idEstudiante) {
        this.idRequerimiento = idRequerimiento;
        this.idEstudiante = idEstudiante;
    }

    public EstadoRequerimientoEstudiantePK() {

    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoRequerimientoEstudiantePK that = (EstadoRequerimientoEstudiantePK) o;
        return Objects.equals(idRequerimiento, that.idRequerimiento) &&
                Objects.equals(idEstudiante, that.idEstudiante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRequerimiento, idEstudiante);
    }
}
