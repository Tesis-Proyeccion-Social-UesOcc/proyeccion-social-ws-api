package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "nombre", nullable = false, length = 45)
	private String nombre;
	
	@Column(name = "descripcion", nullable = false, length = 200)
	private String descripcion;

	public Rol() {
		super();
	}
	
	public Rol(Integer id, String nombre, String descripcion) {
		super();
		Id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	@Override
	public String toString() {
		return "Rol [Id=" + Id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
	
	
}
