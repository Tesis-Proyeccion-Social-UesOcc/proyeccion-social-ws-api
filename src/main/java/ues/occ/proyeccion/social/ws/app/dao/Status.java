	package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "status", nullable = false, length = 50)
	private String status;
	
	@Column(name = "descripcion", nullable = false, length = 200)
	private String descripcion;

	public Status() {
		super();
	}
	
	public Status(Integer id, String status, String descripcion) {
		super();
		Id = id;
		this.status = status;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Status [Id=" + Id + ", status=" + status + ", descripcion=" + descripcion + "]";
	}
	
	
	
}
