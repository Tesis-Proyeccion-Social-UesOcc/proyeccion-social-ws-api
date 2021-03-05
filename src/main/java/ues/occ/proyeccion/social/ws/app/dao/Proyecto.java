package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name = "interno", nullable = false)
    private boolean interno;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tutor")
    private Personal tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_encargado_externo")
    private PersonalExterno encargadoExterno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status")
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Certificado certificado;

    @OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
    private Set<ProyectoEstudiante> proyectoEstudianteSet;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Proyecto() {
        super();
    }

    public Proyecto(String nombre, Integer duracion, boolean interno, Personal tutor, PersonalExterno encargadoExterno, Status status, Set<ProyectoEstudiante> proyectoEstudianteSet) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.interno = interno;
        this.tutor = tutor;
        this.status = status;
        this.encargadoExterno = encargadoExterno;
    }

    public Proyecto(int id, String nombre, int j, boolean interno, LocalDateTime now) {
        this.id = id;
        this.nombre = nombre;
        this.interno = interno;
        this.fechaCreacion = now;

    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public boolean isInterno() {
        return interno;
    }

    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    public Personal getTutor() {
        return tutor;
    }

    public void setTutor(Personal tutor) {
        this.tutor = tutor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PersonalExterno getEncargadoExterno() {
        return encargadoExterno;
    }

    public void setEncargadoExterno(PersonalExterno encargadoExterno) {
        this.encargadoExterno = encargadoExterno;
    }

    public Set<ProyectoEstudiante> getProyectoEstudianteSet() {
        return proyectoEstudianteSet;
    }

    public void setProyectoEstudianteSet(Set<ProyectoEstudiante> proyectoEstudianteSet) {
        this.proyectoEstudianteSet = proyectoEstudianteSet;
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
        Proyecto proyecto = (Proyecto) o;
        return interno == proyecto.interno &&
                Objects.equals(id, proyecto.id) &&
                Objects.equals(nombre, proyecto.nombre) &&
                Objects.equals(duracion, proyecto.duracion) &&
                Objects.equals(tutor, proyecto.tutor) &&
                Objects.equals(status, proyecto.status) &&
                Objects.equals(certificado, proyecto.certificado) &&
                Objects.equals(encargadoExterno, proyecto.encargadoExterno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, duracion, interno, status);
    }
}

