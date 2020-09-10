package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "certificado")
public class Certificado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "uri", nullable = false, length = 2048)
	private String uri;
	
	@Column(name = "fecha_expedicion", nullable = false)
	private Date fecha_expedicion;

	// Indica que la columna id de proyecto_estudiante se usara como PK y FK
	@OneToOne
	@MapsId
	private ProyectoEstudiante proyectoEstudiante;

	public Certificado() {
	}

	public Certificado(Integer id, String uri, Date fecha_expedicion, ProyectoEstudiante proyectoEstudiante) {
		this.id = id;
		this.uri = uri;
		this.fecha_expedicion = fecha_expedicion;
		this.proyectoEstudiante = proyectoEstudiante;
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

	public Date getFecha_expedicion() {
		return fecha_expedicion;
	}

	public void setFecha_expedicion(Date fecha_expedicion) {
		this.fecha_expedicion = fecha_expedicion;
	}

	public ProyectoEstudiante getProyectoEstudiante() {
		return proyectoEstudiante;
	}

	public void setProyectoEstudiante(ProyectoEstudiante proyectoEstudiante) {
		this.proyectoEstudiante = proyectoEstudiante;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Certificado that = (Certificado) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(uri, that.uri) &&
				Objects.equals(fecha_expedicion, that.fecha_expedicion) &&
				Objects.equals(proyectoEstudiante, that.proyectoEstudiante);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uri, fecha_expedicion, proyectoEstudiante);
	}
}
