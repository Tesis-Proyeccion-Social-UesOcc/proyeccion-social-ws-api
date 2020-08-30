package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 100)
	private String apellido;
	
	@Column(name = "email", nullable = false, length = 300)
	private String email;
	
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	
	@Column(name = "id_rol", nullable = false)
	private Integer id_rol;

	
	public Usuario() {
		super();
	}
	
	public Usuario(Integer id, String nombre, String apellido, String email, String password, Integer id_rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.id_rol = id_rol;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId_rol() {
		return id_rol;
	}

	public void setId_rol(Integer id_rol) {
		this.id_rol = id_rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", password=" + password + ", id_rol=" + id_rol + "]";
	}
	
}
