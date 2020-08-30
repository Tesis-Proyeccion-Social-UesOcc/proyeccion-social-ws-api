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
@Table(name = "certificado")
public class Certificado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "uri", nullable = false)
	private String uri;
	
	@Column(name = "fecha_expedicion", nullable = false)
	private Date fecha_expedicion;

	public Integer getId() {
		return id;
	}
	
	public Certificado() {
		
	}
	
	public Certificado(Integer id, String uri, Date fecha_expedicion) {
		super();
		this.id = id;
		this.uri = uri;
		this.fecha_expedicion = fecha_expedicion;
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

	@Override
	public String toString() {
		return "Certificado [id=" + id + ", uri=" + uri + ", fecha_expedicion=" + fecha_expedicion + "]";
	}
	
}
