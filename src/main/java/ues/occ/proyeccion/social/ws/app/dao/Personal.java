package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne()
	@JoinColumn(name = "id_departamento", referencedColumnName = "id")
	private Departamento departamento;

	@ManyToOne()
	@JoinColumn(name = "id_tipo_personal", referencedColumnName = "id")
	private TipoPersonal tipo_personal;

	public Personal() {
		super();
	}

	public Personal(Integer id, String nombre, String apellido, Departamento departamento, TipoPersonal tipo_personal) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.departamento = departamento;
		this.tipo_personal = tipo_personal;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public TipoPersonal getTipo_personal() {
		return tipo_personal;
	}

	public void setTipo_personal(TipoPersonal tipo_personal) {
		this.tipo_personal = tipo_personal;
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

	@Override
	public String toString() {
		return "Personal [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", departamento=" + departamento
				+ ", tipo_personal=" + tipo_personal + "]";
	}

}
