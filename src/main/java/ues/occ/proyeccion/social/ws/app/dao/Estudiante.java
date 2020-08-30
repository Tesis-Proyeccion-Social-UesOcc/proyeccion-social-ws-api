package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "carnet", nullable = false, length = 10)
	private String carnet;
	
	@Column(name = "horas_progreso", nullable = true)
	private Integer horas_progreso;
	
	@Column(name = "servicio_completo", nullable = true, length = 4)
	private Integer servicio_completo;

	public Estudiante() {
		super();
	}
	
	public Estudiante(String carnet, Integer horas_progreso, Integer servicio_completo) {
		super();
		this.carnet = carnet;
		this.horas_progreso = horas_progreso;
		this.servicio_completo = servicio_completo;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public Integer getHoras_progreso() {
		return horas_progreso;
	}

	public void setHoras_progreso(Integer horas_progreso) {
		this.horas_progreso = horas_progreso;
	}

	public Integer getServicio_completo() {
		return servicio_completo;
	}

	public void setServicio_completo(Integer servicio_completo) {
		this.servicio_completo = servicio_completo;
	}

	@Override
	public String toString() {
		return "Estudiante [carnet=" + carnet + ", horas_progreso=" + horas_progreso + ", servicio_completo="
				+ servicio_completo + "]";
	}
	
	
	
}
