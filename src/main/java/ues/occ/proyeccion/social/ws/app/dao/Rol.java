package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

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

	@OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
	private Set<Usuario> usuarios;

	public Rol() {
		super();
	}

	public Rol(Integer id, String nombre, String descripcion, Set<Usuario> usuarios) {
		Id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.usuarios = usuarios;
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

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Rol rol = (Rol) o;
		return Objects.equals(Id, rol.Id) &&
				Objects.equals(nombre, rol.nombre) &&
				Objects.equals(descripcion, rol.descripcion) &&
				Objects.equals(usuarios, rol.usuarios);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id, nombre, descripcion, usuarios);
	}
}
