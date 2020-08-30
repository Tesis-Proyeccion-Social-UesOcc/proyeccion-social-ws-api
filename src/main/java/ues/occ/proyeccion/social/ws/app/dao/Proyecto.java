package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(name = "interno", nullable = false, length = 4)
	private Integer interno;
	
	@Column(name = "id_tutor", nullable = true)
	private Integer id_tutor;
	
	@Column(name = "id_encargado_externo", nullable = true)
	private Integer id_encargado_externo;

	
	public Proyecto() {
		super();
	}
	
	public Proyecto(Integer id, String nombre, Integer duracion, Integer interno, Integer id_tutor,
			Integer id_encargado_externo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.interno = interno;
		this.id_tutor = id_tutor;
		this.id_encargado_externo = id_encargado_externo;
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

	public Integer getInterno() {
		return interno;
	}

	public void setInterno(Integer interno) {
		this.interno = interno;
	}

	public Integer getId_tutor() {
		return id_tutor;
	}

	public void setId_tutor(Integer id_tutor) {
		this.id_tutor = id_tutor;
	}

	public Integer getId_encargado_externo() {
		return id_encargado_externo;
	}

	public void setId_encargado_externo(Integer id_encargado_externo) {
		this.id_encargado_externo = id_encargado_externo;
	}

	@Override
	public String toString() {
		return "Proyecto [id=" + id + ", nombre=" + nombre + ", duracion=" + duracion + ", interno=" + interno
				+ ", id_tutor=" + id_tutor + ", id_encargado_externo=" + id_encargado_externo + "]";
	}
	
}
