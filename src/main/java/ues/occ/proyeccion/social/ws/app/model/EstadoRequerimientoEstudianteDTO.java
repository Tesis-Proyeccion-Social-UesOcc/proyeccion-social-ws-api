package ues.occ.proyeccion.social.ws.app.model;


import java.sql.Date;
import java.util.Objects;

public class EstadoRequerimientoEstudianteDTO{
    private boolean aprobado;

    private Date fechaEntrega;

    public EstadoRequerimientoEstudianteDTO() {
    }

    public EstadoRequerimientoEstudianteDTO(boolean aprobado, Date fechaEntrega) {
        this.aprobado = aprobado;
        this.fechaEntrega = fechaEntrega;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoRequerimientoEstudianteDTO that = (EstadoRequerimientoEstudianteDTO) o;
        return aprobado == that.aprobado &&
                Objects.equals(fechaEntrega, that.fechaEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aprobado, fechaEntrega);
    }
}
