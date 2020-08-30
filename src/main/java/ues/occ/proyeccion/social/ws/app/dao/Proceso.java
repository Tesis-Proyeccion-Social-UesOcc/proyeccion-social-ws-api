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
@Table(name = "proceso")
public class Proceso implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "descripcion", nullable = false, length = 45)
	private String descripcion;
	
	@Column(name = "fecha_inicio")
	private Date fecha_inicio;
	
	@Column(name = "fecha_fin")
	private Date fecha_fin;

	public Proceso() {
		super();
	}
	
	public Proceso(Integer id, String nombre, String descripcion, Date fecha_inicio, Date fecha_fin) {
		super();
		Id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
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

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	@Override
	public String toString() {
		return "Proceso [Id=" + Id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha_inicio="
				+ fecha_inicio + ", fecha_fin=" + fecha_fin + "]";
	}
		
}
