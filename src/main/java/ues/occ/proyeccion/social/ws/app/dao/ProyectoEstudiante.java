package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proyecto_estudiante")
public class ProyectoEstudiante implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "carnet", nullable = false)
	private String carnet;
	
	@Column(name = "id_proyecto", nullable = false)
	private Integer id_proyecto;
	
	@Column(name = "id_status", nullable = false)
	private Integer id_status;

	public ProyectoEstudiante() {
	}
	
	public ProyectoEstudiante(Integer id, String carnet, Integer id_proyecto, Integer id_status) {
		super();
		this.id = id;
		this.carnet = carnet;
		this.id_proyecto = id_proyecto;
		this.id_status = id_status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public Integer getId_proyecto() {
		return id_proyecto;
	}

	public void setId_proyecto(Integer id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	public Integer getId_status() {
		return id_status;
	}

	public void setId_status(Integer id_status) {
		this.id_status = id_status;
	}

	@Override
	public String toString() {
		return "ProyectoEstudiante [id=" + id + ", carnet=" + carnet + ", id_proyecto=" + id_proyecto + ", id_status="
				+ id_status + "]";
	}
	
	
}
