package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "personal")
public class Personal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 200)
	private String apellido;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_departamento", referencedColumnName = "id")
	private Departamento departamento;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_personal", referencedColumnName = "id")
	private TipoPersonal tipoPersonal;

	@OneToMany(mappedBy = "tutor", fetch = FetchType.LAZY)
	private Set<Proyecto> proyectos;

	@OneToOne(mappedBy = "personal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PersonalEncargado personalEncargado;


	public Personal() {
		super();
	}


	public Personal(Integer id, String nombre, String apellido, Departamento departamento, TipoPersonal tipoPersonal) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.departamento = departamento;
		this.tipoPersonal = tipoPersonal;
	}

	public Personal(Integer id, String nombre, String apellido){
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}



	public PersonalEncargado getPersonalEncargado() {
		return personalEncargado;
	}


	public void setPersonalEncargado(PersonalEncargado personalEncargado) {
		this.personalEncargado = personalEncargado;
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


	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}


	public TipoPersonal getTipoPersonal() {
		return tipoPersonal;
	}


	public void setTipoPersonal(TipoPersonal tipoPersonal) {
		this.tipoPersonal = tipoPersonal;
	}


	
	
}
