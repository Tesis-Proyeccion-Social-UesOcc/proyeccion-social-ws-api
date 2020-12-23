package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "proyecto_estudiante"
        , uniqueConstraints = @UniqueConstraint(
        columnNames = {"carnet", "id_proyecto"}))
public class ProyectoEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carnet")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status")
    private Status status;

    @OneToOne(mappedBy = "proyectoEstudiante", cascade = CascadeType.ALL)
    private Certificado certificado;

    public ProyectoEstudiante() {
    }

    public ProyectoEstudiante(Estudiante estudiante, Proyecto proyecto, Status status) {
        this.estudiante = estudiante;
        this.proyecto = proyecto;
        this.status = status;
    }

    public Integer getId() {
        return id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoEstudiante that = (ProyectoEstudiante) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(estudiante, that.estudiante) &&
                Objects.equals(proyecto, that.proyecto) &&
                Objects.equals(status, that.status) &&
                Objects.equals(certificado, that.certificado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estudiante, proyecto, status, certificado);
    }
}
