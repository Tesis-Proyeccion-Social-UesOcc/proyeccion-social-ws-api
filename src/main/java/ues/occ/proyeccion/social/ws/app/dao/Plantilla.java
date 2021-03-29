package ues.occ.proyeccion.social.ws.app.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table("plantilla")
public class Plantilla implements Serializable {

    @Id
    private Integer id_plantilla;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "url", nullable = false)
    private String url;

    public Plantilla() {
    }

    public Plantilla(int id_plantilla, String nombre, String url) {
        this.id_plantilla = id_plantilla;
        this.nombre = nombre;
        this.url = url;
    }

    public int getId_plantilla() {
        return id_plantilla;
    }

    public void setId_plantilla(int id_plantilla) {
        this.id_plantilla = id_plantilla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plantilla plantilla = (Plantilla) o;
        return id_plantilla == plantilla.id_plantilla && Objects.equals(nombre, plantilla.nombre) && Objects.equals(url, plantilla.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_plantilla, nombre, url);
    }
}
