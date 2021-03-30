package ues.occ.proyeccion.social.ws.app.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "plantilla")
public class Plantilla implements Serializable {

    @Id
    private Integer idPlantilla;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "url", nullable = false)
    private String url;

    public Plantilla() {
    }

    public Plantilla(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
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
        return Objects.equals(idPlantilla, plantilla.idPlantilla) && Objects.equals(nombre, plantilla.nombre) && Objects.equals(url, plantilla.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlantilla, nombre, url);
    }
}
