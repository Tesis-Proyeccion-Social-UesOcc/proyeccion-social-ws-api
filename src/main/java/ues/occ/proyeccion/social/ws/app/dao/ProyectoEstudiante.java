package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @OneToMany(mappedBy = "proyectoEstudiante", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes = new ArrayList<>();

    @Column(name = "activo")
    private Boolean active;

    public ProyectoEstudiante() {
    }

    public ProyectoEstudiante(Estudiante estudiante, Proyecto proyecto, Boolean active) {
        this.estudiante = estudiante;
        this.proyecto = proyecto;
        this.active = active;
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

    public void addRequerimiento(Requerimiento requerimiento, boolean aprobado){
        var requerimientoEstudiante = new EstadoRequerimientoEstudiante(this, requerimiento, true,
                aprobado, new Date(System.currentTimeMillis()), null);
        this.estadoRequerimientoEstudiantes.add(requerimientoEstudiante);
        requerimiento.getEstadoRequerimientoEstudiantes().add(requerimientoEstudiante);
    }

    public List<EstadoRequerimientoEstudiante> getEstadoRequerimientoEstudiantes() {
        return estadoRequerimientoEstudiantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoEstudiante that = (ProyectoEstudiante) o;
        return estudiante.equals(that.estudiante) && proyecto.equals(that.proyecto) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, proyecto, active);
    }
}

