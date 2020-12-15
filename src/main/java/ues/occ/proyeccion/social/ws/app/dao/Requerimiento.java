package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

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

    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY)
    private Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes;

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
        return Objects.hash(id, original, cantidadCopias, proceso, documento, estadoRequerimientoEstudiantes);
    }
}
