package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
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
	private Integer id;
	
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;
	
	@Column(name = "descripcion", nullable = false, length = 1000)
	private String descripcion;
	
	@OneToMany(targetEntity = Personal.class, mappedBy = "tipoPersonal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Personal> personal;
	
	public TipoPersonal() {
		super();
	}

	public TipoPersonal(Integer id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoPersonal [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TipoPersonal that = (TipoPersonal) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(nombre, that.nombre) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(personal, that.personal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, descripcion, personal);
	}
}
