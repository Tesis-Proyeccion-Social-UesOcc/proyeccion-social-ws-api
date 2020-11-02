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
	
	@Column(name = "servicio_completo", nullable = true)
	private boolean servicio_completo;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private Set<ProyectoEstudiante> proyectoEstudianteSet;

	public Estudiante() {
		super();
	}

	public Estudiante(String carnet, Integer horas_progreso, boolean servicio_completo, Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes, Set<ProyectoEstudiante> proyectoEstudianteSet) {
		this.carnet = carnet;
		this.horas_progreso = horas_progreso;
		this.servicio_completo = servicio_completo;
		this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
		this.proyectoEstudianteSet = proyectoEstudianteSet;
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

	public boolean isServicio_completo() {
		return servicio_completo;
	}

	public void setServicio_completo(boolean servicio_completo) {
		this.servicio_completo = servicio_completo;
	}

	public Set<EstadoRequerimientoEstudiante> getEstadoRequerimientoEstudiantes() {
		return estadoRequerimientoEstudiantes;
	}

	public void setEstadoRequerimientoEstudiantes(Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes) {
		this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
	}

	public Set<ProyectoEstudiante> getProyectoEstudianteSet() {
		return proyectoEstudianteSet;
	}

	public void setProyectoEstudianteSet(Set<ProyectoEstudiante> proyectoEstudianteSet) {
		this.proyectoEstudianteSet = proyectoEstudianteSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Estudiante that = (Estudiante) o;
		return servicio_completo == that.servicio_completo &&
				Objects.equals(carnet, that.carnet) &&
				Objects.equals(horas_progreso, that.horas_progreso) &&
				Objects.equals(estadoRequerimientoEstudiantes, that.estadoRequerimientoEstudiantes) &&
				Objects.equals(proyectoEstudianteSet, that.proyectoEstudianteSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(carnet, horas_progreso, servicio_completo, estadoRequerimientoEstudiantes, proyectoEstudianteSet);
	}
}
