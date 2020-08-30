package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personal")
public class Personal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellido", nullable = false)
	private String apellido;

	@Column(name = " id_tipo_personal", nullable = false)
	private Integer id_tipo_personal;

	@Column(name = " id_departamento", nullable = false)
	private Integer id_departamento;

	public Personal() {
		super();
	}

	public Personal(Integer id, String nombre, String apellido, Integer id_tipo_personal, Integer id_departamento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.id_tipo_personal = id_tipo_personal;
		this.id_departamento = id_departamento;
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

	public Integer getId_tipo_personal() {
		return id_tipo_personal;
	}

	public void setId_tipo_personal(Integer id_tipo_personal) {
		this.id_tipo_personal = id_tipo_personal;
	}

	public Integer getId_departamento() {
		return id_departamento;
	}

	public void setId_departamento(Integer id_departamento) {
		this.id_departamento = id_departamento;
	}

	@Override
	public String toString() {
		return "Personal [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", id_tipo_personal="
				+ id_tipo_personal + ", id_departamento=" + id_departamento + "]";
	}

}
