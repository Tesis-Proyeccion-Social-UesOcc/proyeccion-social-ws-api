package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "documento")
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "fecha_documento", nullable = false)
    private LocalDateTime fechaDocumento;

    @OneToMany(mappedBy = "documento", fetch = FetchType.LAZY)
    private Set<Requerimiento> requerimientos;

    public Documento() {
        super();
    }

    public Documento(String nombre, String descripcion, String uri, LocalDateTime fecha_documento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.uri = uri;
        this.fechaDocumento = fecha_documento;
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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public LocalDateTime getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(LocalDateTime fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

   

}
