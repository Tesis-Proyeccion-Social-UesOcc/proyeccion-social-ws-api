package ues.occ.proyeccion.social.ws.app.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "proyecto_estudiante"
        , uniqueConstraints = @UniqueConstraint(
        columnNames = {"carnet", "id_proyecto"}))
public class ProyectoEstudiante implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto_estudiante")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carnet")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @OneToMany(mappedBy = "proyectoEstudiante", fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE,
            orphanRemoval = true)
    private List<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto_estudiante", referencedColumnName = "id")
    private Certificado certificado;

    @Column(name = "activo")
    private Boolean active;

    public ProyectoEstudiante() {
    }

    public ProyectoEstudiante(Estudiante estudiante, Proyecto proyecto, Boolean active) {
        this.estudiante = estudiante;
        this.proyecto = proyecto;
        this.active = active;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Integer getId() {
        return id;
    }

    public List<EstadoRequerimientoEstudiante> getEstadoRequerimientoEstudiantes() {
        return estadoRequerimientoEstudiantes;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoEstudiante that = (ProyectoEstudiante) o;
        return estudiante.equals(that.estudiante) && proyecto.equals(that.proyecto)
                && Objects.equals(active, that.active) && certificado.equals(that.certificado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, proyecto, active);
    }

    @Override
    public String toString() {
        return "ProyectoEstudiante{" +
                "id=" + id +
                ", estudiante=" + estudiante.getCarnet() +
                ", active=" + active +
                '}';
    }
}

