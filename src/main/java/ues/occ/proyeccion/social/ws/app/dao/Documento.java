package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "documento")
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "fecha_documento", nullable = false)
    private Date fecha_documento;

    @OneToMany(mappedBy = "documento", fetch = FetchType.LAZY)
    private Set<Requerimiento> requerimientos;

    public Documento() {
        super();
    }

    public Documento(Integer id, String nombre, String descripcion, String uri, Date fecha_documento, Set<Requerimiento> requerimientos) {
        Id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.uri = uri;
        this.fecha_documento = fecha_documento;
        this.requerimientos = requerimientos;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getFecha_documento() {
        return fecha_documento;
    }

    public void setFecha_documento(Date fecha_documento) {
        this.fecha_documento = fecha_documento;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Documento documento = (Documento) o;
        return Objects.equals(Id, documento.Id) &&
                Objects.equals(nombre, documento.nombre) &&
                Objects.equals(descripcion, documento.descripcion) &&
                Objects.equals(uri, documento.uri) &&
                Objects.equals(fecha_documento, documento.fecha_documento) &&
                Objects.equals(requerimientos, documento.requerimientos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, nombre, descripcion, uri, fecha_documento, requerimientos);
    }
}
