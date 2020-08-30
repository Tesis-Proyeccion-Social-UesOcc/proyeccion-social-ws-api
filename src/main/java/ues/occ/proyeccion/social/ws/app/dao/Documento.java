package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documento")
public class Documento implements Serializable{

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
	private Date fecha_documento;

	public Documento() {
		super();
	}
	
	public Documento(Integer id, String nombre, String descripcion, String uri, Date fecha_documento) {
		super();
		Id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.uri = uri;
		this.fecha_documento = fecha_documento;
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

	public Date getFecha_documento() {
		return fecha_documento;
	}

	public void setFecha_documento(Date fecha_documento) {
		this.fecha_documento = fecha_documento;
	}

	@Override
	public String toString() {
		return "Documento [Id=" + Id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", uri=" + uri
				+ ", fecha_documento=" + fecha_documento + "]";
	}
	
	
}
