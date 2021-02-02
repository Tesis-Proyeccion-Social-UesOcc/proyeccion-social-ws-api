package ues.occ.proyeccion.social.ws.app.dao;


import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "estado_requerimiento_estudiante")
public class EstadoRequerimientoEstudiante implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private EstadoRequerimientoEstudiantePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRequerimiento")
    @JoinColumn(name = "id_requerimiento")
    private Requerimiento requerimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

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

    public EstadoRequerimientoEstudiante(Estudiante estudiante, Requerimiento requerimiento, boolean entregado, boolean aprobado, Date fechaEntrega, Date fechaAprobacion) {
        this.id = new EstadoRequerimientoEstudiantePK(requerimiento.getId(), estudiante.getCarnet());
        this.estudiante = estudiante;
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
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
                Objects.equals(estudiante, that.estudiante) &&
                Objects.equals(fechaEntrega, that.fechaEntrega) &&
                Objects.equals(fechaAprobacion, that.fechaAprobacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entregado, aprobado, fechaEntrega, fechaAprobacion);
    }
}
