package ues.occ.proyeccion.social.ws.app.dao;


import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "estado_requerimiento_estudiante")
@FilterDef(name = "studentRequirement", parameters = {@ParamDef(name = "project", type = "String"), @ParamDef(name = "carnet", type = "String")})
@FilterDef(name = "studentRequirement2")
public class EstadoRequerimientoEstudiante implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private EstadoRequerimientoEstudiantePK id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("idRequerimiento")
    @JoinColumn(name = "id_requerimiento")
    private Requerimiento requerimiento;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @MapsId("idProyectoEstudiante")
    @JoinColumn(name = "id_proyecto_estudiante")
    private ProyectoEstudiante proyectoEstudiante;

    @Column(name = "entregado")
    private boolean entregado;

    @Column(name = "aprobado")
    private boolean aprobado;

    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @Column(name = "fecha_aprobacion")
    private Date fechaAprobacion;

    public EstadoRequerimientoEstudiante() {
    }

    public EstadoRequerimientoEstudiante(ProyectoEstudiante proyectoEstudiante, Requerimiento requerimiento, boolean entregado, boolean aprobado, Date fechaEntrega, Date fechaAprobacion) {
        this.id = new EstadoRequerimientoEstudiantePK(requerimiento.getId(), proyectoEstudiante.getId());
        this.proyectoEstudiante = proyectoEstudiante;
        this.requerimiento = requerimiento;
        this.entregado = entregado;
        this.aprobado = aprobado;
        this.fechaEntrega = fechaEntrega;
        this.fechaAprobacion = fechaAprobacion;
    }

    public EstadoRequerimientoEstudiantePK getId() {
        return id;
    }

    public void setId(EstadoRequerimientoEstudiantePK id) {
        this.id = id;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public ProyectoEstudiante getProyectoEstudiante() {
        return proyectoEstudiante;
    }

    public void setProyectoEstudiante(ProyectoEstudiante proyectoEstudiante) {
        this.proyectoEstudiante = proyectoEstudiante;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoRequerimientoEstudiante that = (EstadoRequerimientoEstudiante) o;
        return entregado == that.entregado &&
                aprobado == that.aprobado &&
                Objects.equals(id, that.id) &&
                Objects.equals(requerimiento, that.requerimiento) &&
                Objects.equals(proyectoEstudiante, that.proyectoEstudiante) &&
                Objects.equals(fechaEntrega, that.fechaEntrega) &&
                Objects.equals(fechaAprobacion, that.fechaAprobacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entregado, aprobado, fechaEntrega, fechaAprobacion);
    }

    @Override
    public String toString() {
        return "EstadoRequerimientoEstudiante{" +
                "proyectoEstudiante=" + proyectoEstudiante +
                '}';
    }
}
