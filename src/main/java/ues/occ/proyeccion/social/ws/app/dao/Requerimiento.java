package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "requerimiento")
public class Requerimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "original", nullable = false, length = 4)
    private Integer original;

    @Column(name = "cantidad_copias", nullable = true)
    private Integer cantidad_copias;

    @Column(name = "id_proceso", nullable = true)
    private Integer id_proceso;

    @Column(name = "id_documento", nullable = true)
    private Integer id_documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proceso")
    private Proceso proceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documento")
    private Documento documento;

    public Requerimiento() {
        super();
    }

    public Requerimiento(Integer id, Integer original, Integer cantidad_copias, Integer id_proceso, Integer id_documento, Proceso proceso, Documento documento) {
        this.id = id;
        this.original = original;
        this.cantidad_copias = cantidad_copias;
        this.id_proceso = id_proceso;
        this.id_documento = id_documento;
        this.proceso = proceso;
        this.documento = documento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Integer getCantidad_copias() {
        return cantidad_copias;
    }

    public void setCantidad_copias(Integer cantidad_copias) {
        this.cantidad_copias = cantidad_copias;
    }

    public Integer getId_proceso() {
        return id_proceso;
    }

    public void setId_proceso(Integer id_proceso) {
        this.id_proceso = id_proceso;
    }

    public Integer getId_documento() {
        return id_documento;
    }

    public void setId_documento(Integer id_documento) {
        this.id_documento = id_documento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requerimiento that = (Requerimiento) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(original, that.original) &&
                Objects.equals(cantidad_copias, that.cantidad_copias) &&
                Objects.equals(id_proceso, that.id_proceso) &&
                Objects.equals(id_documento, that.id_documento) &&
                Objects.equals(proceso, that.proceso) &&
                Objects.equals(documento, that.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, original, cantidad_copias, id_proceso, id_documento, proceso, documento);
    }
}
