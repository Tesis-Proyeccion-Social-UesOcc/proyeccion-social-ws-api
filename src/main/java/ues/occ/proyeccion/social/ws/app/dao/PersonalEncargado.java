package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personal_encargado")
public class PersonalEncargado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "horario", length = 45, nullable = false)
	private String horario;
	
	@Column(name = "ubicacion", length = 200, nullable = false)
	private String ubicacion;

	public PersonalEncargado() {
		super();
	}
	
	public PersonalEncargado(Integer id, String horario, String ubicacion) {
		super();
		this.id = id;
		this.horario = horario;
		this.ubicacion = ubicacion;
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

	@Override
	public String toString() {
		return "PersonalEncargado [id=" + id + ", horario=" + horario + ", ubicacion=" + ubicacion + "]";
	}
	
	
	
}
