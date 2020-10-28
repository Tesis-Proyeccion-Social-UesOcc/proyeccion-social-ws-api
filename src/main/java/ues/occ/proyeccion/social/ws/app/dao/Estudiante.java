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
	private Integer horasProgreso;
	
	@Column(name = "servicio_completo", nullable = true)
	private boolean servicioCompleto;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private Set<ProyectoEstudiante> proyectoEstudianteSet;

	public Estudiante() {
		super();
	}

	public Estudiante(String carnet, Integer horasProgreso, boolean servicioCompleto, Set<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes, Set<ProyectoEstudiante> proyectoEstudianteSet) {
		this.carnet = carnet;
		this.horasProgreso = horasProgreso;
		this.servicioCompleto = servicioCompleto;
		this.estadoRequerimientoEstudiantes = estadoRequerimientoEstudiantes;
		this.proyectoEstudianteSet = proyectoEstudianteSet;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public Integer getHorasProgreso() {
		return horasProgreso;
	}

	public void setHorasProgreso(Integer horasProgreso) {
		this.horasProgreso = horasProgreso;
	}

	public boolean isServicioCompleto() {
		return servicioCompleto;
	}

	public void setServicioCompleto(boolean servicioCompleto) {
		this.servicioCompleto = servicioCompleto;
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
		return servicioCompleto == that.servicioCompleto &&
				carnet.equals(that.carnet) &&
				Objects.equals(horasProgreso, that.horasProgreso) &&
				Objects.equals(estadoRequerimientoEstudiantes, that.estadoRequerimientoEstudiantes) &&
				Objects.equals(proyectoEstudianteSet, that.proyectoEstudianteSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(carnet, horasProgreso, servicioCompleto, estadoRequerimientoEstudiantes, proyectoEstudianteSet);
	}
}
