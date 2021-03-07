package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "personal_externo")
public class PersonalExterno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 100)
	private String apellido;

	@Column(name = "descripcion", nullable = false, length = 500)
	private String descripcion;

	@Column(name = "email", nullable = false, length = 254)
	private String email;

	@OneToMany(mappedBy = "encargadoExterno", fetch = FetchType.LAZY)
	private List<Proyecto> proyectos;
	
	public PersonalExterno() {
		super();
	}

	public PersonalExterno(Integer id, String nombre, String apellido, String descripcion, String email, List<Proyecto> proyectos) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.descripcion = descripcion;
		this.email = email;
		this.proyectos = proyectos;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonalExterno that = (PersonalExterno) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(nombre, that.nombre) &&
				Objects.equals(apellido, that.apellido) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(email, that.email) &&
				Objects.equals(proyectos, that.proyectos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, apellido, descripcion, email, proyectos);
	}
}
