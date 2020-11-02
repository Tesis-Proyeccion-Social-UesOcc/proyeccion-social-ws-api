package ues.occ.proyeccion.social.ws.app.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "proceso")
public class Proceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 45)
    private String descripcion;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;

    @OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY)
    private Set<Requerimiento> requerimientos;

    public Proceso() {
        super();
    }

    public Proceso(Integer id, String nombre, String descripcion, Date fecha_inicio, Date fecha_fin, Set<Requerimiento> requerimientos) {
        Id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.requerimientos = requerimientos;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
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
        return Objects.equals(Id, proceso.Id) &&
                Objects.equals(nombre, proceso.nombre) &&
                Objects.equals(descripcion, proceso.descripcion) &&
                Objects.equals(fecha_inicio, proceso.fecha_inicio) &&
                Objects.equals(fecha_fin, proceso.fecha_fin) &&
                Objects.equals(requerimientos, proceso.requerimientos);
    }

    // ide generated
    @Override
    public int hashCode() {
        return Objects.hash(Id, nombre, descripcion, fecha_inicio, fecha_fin, requerimientos);
    }
}
