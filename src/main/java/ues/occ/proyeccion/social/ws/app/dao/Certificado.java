package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "certificado")
public class Certificado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "uri", nullable = false, length = 2048)
	private String uri;

	@Column(name = "fecha_expedicion", nullable = false)
	private LocalDateTime fechaExpedicion;

	// Indica que la columna id de proyecto_estudiante se usara como PK y FK

	@OneToOne(mappedBy = "certificado")
	private ProyectoEstudiante proyectoEstudiante;

	public Certificado() {
	}

	public Certificado(Integer id, String uri, LocalDateTime fechaExpedicion) {
		this.id = id;
		this.uri = uri;
		this.fechaExpedicion = fechaExpedicion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public LocalDateTime getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(LocalDateTime fecha_expedicion) {
		this.fechaExpedicion = fecha_expedicion;
	}

	public ProyectoEstudiante getProyectoEstudiante() {
		return proyectoEstudiante;
	}

	public void setProyectoEstudiante(ProyectoEstudiante proyectoEstudiante) {
		this.proyectoEstudiante = proyectoEstudiante;
	}
	
	@Override
	public String toString() {
		return "Certificado [id=" + id + ", uri=" + uri + ", fecha_expedicion=" + fechaExpedicion
				+ ", proyectoEstudiante=" + proyectoEstudiante + "]";
	}

}
