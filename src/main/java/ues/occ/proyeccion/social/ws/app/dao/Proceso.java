package ues.occ.proyeccion.social.ws.app.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "proceso")
public class Proceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 45, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY)
    private Set<Requerimiento> requerimientos;

    public Proceso() {
        super();
    }

    public Proceso(Integer id, String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fecha_inicio) {
        this.fechaInicio = fecha_inicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fecha_fin) {
        this.fechaFin = fecha_fin;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

    // ide generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proceso proceso = (Proceso) o;
        return Objects.equals(id, proceso.id) &&
                Objects.equals(nombre, proceso.nombre) &&
                Objects.equals(descripcion, proceso.descripcion) &&
                Objects.equals(fechaInicio, proceso.fechaInicio) &&
                Objects.equals(fechaFin, proceso.fechaFin) &&
                Objects.equals(requerimientos, proceso.requerimientos);
    }

    // ide generated
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, fechaInicio, fechaFin, requerimientos);
    }
}
