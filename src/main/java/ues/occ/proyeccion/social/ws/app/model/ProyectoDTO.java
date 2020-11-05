package ues.occ.proyeccion.social.ws.app.model;

public class ProyectoDTO {
    private String nombre;
    private Integer duracion;
    private boolean interno;

    public ProyectoDTO(String nombre, Integer duracion, boolean interno) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.interno = interno;
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
}
