package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "duracion", nullable = false)
	private Integer duracion;
	
	@Column(name = "interno", nullable = false)
	private boolean interno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tutor")
	private Personal tutor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_encargado_externo")
	private PersonalExterno encargadoExterno;

	@OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
	private Set<ProyectoEstudiante> proyectoEstudianteSet;

	
	public Proyecto() {
		super();
	}

	public Proyecto(Integer id, String nombre, Integer duracion, boolean interno, Personal tutor, PersonalExterno encargadoExterno, Set<ProyectoEstudiante> proyectoEstudianteSet) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.interno = interno;
		this.tutor = tutor;
		this.encargadoExterno = encargadoExterno;
		this.proyectoEstudianteSet = proyectoEstudianteSet;
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

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	public Personal getTutor() {
		return tutor;
	}

	public void setTutor(Personal tutor) {
		this.tutor = tutor;
	}

	public PersonalExterno getEncargadoExterno() {
		return encargadoExterno;
	}

	public void setEncargadoExterno(PersonalExterno encargadoExterno) {
		this.encargadoExterno = encargadoExterno;
	}

	public Set<ProyectoEstudiante> getProyectoEstudianteSet() {
		return proyectoEstudianteSet;
	}

	public void setProyectoEstudianteSet(Set<ProyectoEstudiante> proyectoEstudianteSet) {
		this.proyectoEstudianteSet = proyectoEstudianteSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Proyecto proyecto = (Proyecto) o;
		return interno == proyecto.interno &&
				Objects.equals(id, proyecto.id) &&
				Objects.equals(nombre, proyecto.nombre) &&
				Objects.equals(duracion, proyecto.duracion) &&
				Objects.equals(tutor, proyecto.tutor) &&
				Objects.equals(encargadoExterno, proyecto.encargadoExterno) &&
				Objects.equals(proyectoEstudianteSet, proyecto.proyectoEstudianteSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, duracion, interno, tutor, encargadoExterno, proyectoEstudianteSet);
	}
}
