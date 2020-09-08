package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "departamento")
public class Departamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;
	
	@OneToMany(targetEntity = Personal.class, mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Personal> personal;

	public Departamento() {
		super();
	}
	
	public Departamento(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Departamento that = (Departamento) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(nombre, that.nombre) &&
				Objects.equals(personal, that.personal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, personal);
	}
}
