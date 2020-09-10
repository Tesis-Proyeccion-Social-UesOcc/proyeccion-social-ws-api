package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "personal_encargado")
public class PersonalEncargado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "horario", length = 45, nullable = false)
	private String horario;
	
	@Column(name = "ubicacion", length = 200, nullable = false)
	private String ubicacion;

	// Indica que la columna id de personal se usara como PK y FK
	@OneToOne
	@MapsId
	private Personal personal;

	public PersonalEncargado() {
		super();
	}

	public PersonalEncargado(Integer id, String horario, String ubicacion, Personal personal) {
		this.id = id;
		this.horario = horario;
		this.ubicacion = ubicacion;
		this.personal = personal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonalEncargado that = (PersonalEncargado) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(horario, that.horario) &&
				Objects.equals(ubicacion, that.ubicacion) &&
				Objects.equals(personal, that.personal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, horario, ubicacion, personal);
	}
}
