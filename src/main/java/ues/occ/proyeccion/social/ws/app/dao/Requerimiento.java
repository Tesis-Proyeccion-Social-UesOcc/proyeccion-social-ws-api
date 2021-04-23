package ues.occ.proyeccion.social.ws.app.dao;

import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "requerimiento")
public class Requerimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "original", nullable = false)
    private boolean original;

    @Column(name = "cantidad_copias", nullable = true)
    private Integer cantidadCopias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proceso")
    private Proceso proceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documento")
    private Documento documento;

    @Filter(name = "studentRequirement2", condition = "id_proyecto_estudiante = 84")
    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes = new HashSet<>();

    public Requerimiento() {
        super();
    }

    public Requerimiento(Integer id, boolean original, Integer cantidadCopias, Proceso proceso, Documento documento, Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes) {
        this.id = id;
        this.original = original;
        this.cantidadCopias = cantidadCopias;
        this.proceso = proceso;
        this.documento = documento;
        this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
    }

    public Requerimiento(Integer id, boolean original, Integer cantidadCopias, Documento documento) {
        this.id = id;
        this.original = original;
        this.cantidadCopias = cantidadCopias;
        this.documento = documento;
        documento.getRequerimientos().add(this);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public Integer getcantidadCopias() {
        return cantidadCopias;
    }

    public void setcantidadCopias(Integer cantidadCopias) {
        this.cantidadCopias = cantidadCopias;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Set<EstadoRequerimientoEstudiante> getEstadoRequerimientoEstudiantes() {
        return estadoRequerimientoEstudiantes;
    }

    public void addEstadoRequerimiento(ProyectoEstudiante proyectoEstudiante, boolean aprobado){
        var requerimientoEstudiante = new EstadoRequerimientoEstudiante(proyectoEstudiante, this, true,
                aprobado, new Date(System.currentTimeMillis()), null);
        this.estadoRequerimientoEstudiantes.add(requerimientoEstudiante);
        proyectoEstudiante.getEstadoRequerimientoEstudiantes().add(requerimientoEstudiante);

    }

    public void setEstadoRequerimientoEstudiantes(Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes) {
        this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requerimiento that = (Requerimiento) o;
        return original == that.original &&
                Objects.equals(id, that.id) &&
                Objects.equals(cantidadCopias, that.cantidadCopias) &&
                Objects.equals(proceso, that.proceso) &&
                Objects.equals(documento, that.documento) &&
                Objects.equals(estadoRequerimientoEstudiantes, that.estadoRequerimientoEstudiantes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, original, cantidadCopias);
    }

    @Override
    public String toString() {
        return "Requerimiento{" +
                "estadoRequerimientoEstudiantes=" + estadoRequerimientoEstudiantes +
                '}';
    }
}
