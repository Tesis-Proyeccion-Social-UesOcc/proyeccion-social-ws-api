package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_personal")
public class TipoPersonal implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	@OneToMany(targetEntity = Personal.class, mappedBy = "tipo_personal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Personal> personal;
	
	public TipoPersonal() {
		super();
	}

	public TipoPersonal(Integer id, String nombre, String descripcion) {
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
		return "TipoPersonal [Id=" + Id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
	
	
}
