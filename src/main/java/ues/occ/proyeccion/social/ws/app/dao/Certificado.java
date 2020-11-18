package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "certificado")
public class Certificado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@JsonIgnore
	@Column(name = "uri", nullable = false, length = 2048)
	private String uri;

	@Transient
	private String base64File;

	@Transient
	private String nameFile;

	@Column(name = "fecha_expedicion", nullable = false)
	private Date fechaExpedicion;

	// Indica que la columna id de proyecto_estudiante se usara como PK y FK

	@OneToOne(mappedBy = "certificado")
	private ProyectoEstudiante proyectoEstudiante;

	public Certificado() {
	}

	public Certificado(Integer id, String uri, Date fechaExpedicion) {
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

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fecha_expedicion) {
		this.fechaExpedicion = fecha_expedicion;
	}

	public ProyectoEstudiante getProyectoEstudiante() {
		return proyectoEstudiante;
	}

	public void setProyectoEstudiante(ProyectoEstudiante proyectoEstudiante) {
		this.proyectoEstudiante = proyectoEstudiante;
	}

	public String getBase64File() {
		return base64File;
	}

	public void setBase64File(String base64File) {
		this.base64File = base64File;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	@Override
	public String toString() {
		return "Certificado [id=" + id + ", uri=" + uri + ", fecha_expedicion=" + fechaExpedicion
				+ ", proyectoEstudiante=" + proyectoEstudiante + "]";
	}

}
