package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

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

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes;


	public Estudiante() {
		super();
	}

	public Estudiante(String carnet, Integer horas_progreso, Integer servicio_completo, Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes) {
		this.carnet = carnet;
		this.horas_progreso = horas_progreso;
		this.servicio_completo = servicio_completo;
		this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
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

	public Set<EstadoRequerimientoEstudiante> getEstadoRequerimientoEstudiantes() {
		return estadoRequerimientoEstudiantes;
	}

	public void setEstadoRequerimientoEstudiantes(Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes) {
		this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Estudiante that = (Estudiante) o;
		return Objects.equals(carnet, that.carnet) &&
				Objects.equals(horas_progreso, that.horas_progreso) &&
				Objects.equals(servicio_completo, that.servicio_completo) &&
				Objects.equals(estadoRequerimientoEstudiantes, that.estadoRequerimientoEstudiantes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(carnet, horas_progreso, servicio_completo, estadoRequerimientoEstudiantes);
	}
}
