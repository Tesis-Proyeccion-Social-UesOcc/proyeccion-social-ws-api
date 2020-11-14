package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;
import java.util.Objects;

public class ProyectoDTO implements Serializable {

    private String nombre;
    private Integer duracion;
    private boolean interno;
    private int personal;

    public ProyectoDTO(){super();}

    public ProyectoDTO(String nombre, Integer duracion, boolean interno, int personal) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.interno = interno;  // if is true, personal value is Personal ID otherwise PersonalExterno ID
        this.personal = personal;  // An integer will be used instead of PersonalDTO
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public boolean isInterno() {
        return interno;
    }

    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoDTO that = (ProyectoDTO) o;
        return interno == that.interno &&
                personal == that.personal &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(duracion, that.duracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, duracion, interno, personal);
    }
}
