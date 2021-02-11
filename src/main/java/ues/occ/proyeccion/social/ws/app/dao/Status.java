package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<Proyecto> proyectos;

    public Status() {
        super();
    }

    public Status(Integer id, String status, String descripcion) {
        this.id = id;
        this.status = status;
        this.descripcion = descripcion;
    }

    public Status(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status1 = (Status) o;
        return Objects.equals(this.id, status1.id) &&
                Objects.equals(status, status1.status) &&
                Objects.equals(descripcion, status1.descripcion) &&
                Objects.equals(proyectos, status1.proyectos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, descripcion, proyectos);
    }
}
