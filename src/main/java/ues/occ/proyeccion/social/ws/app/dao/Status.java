	package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

	@Entity
@Table(name = "status")
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "status", nullable = false, length = 50)
	private String status;
	
	@Column(name = "descripcion", nullable = false, length = 200)
	private String descripcion;

	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private Set<ProyectoEstudiante> proyectoEstudianteSet;

	public Status() {
		super();
	}

		public Status(Integer id, String status, String descripcion, Set<ProyectoEstudiante> proyectoEstudianteSet) {
			Id = id;
			this.status = status;
			this.descripcion = descripcion;
			this.proyectoEstudianteSet = proyectoEstudianteSet;
		}

		public Integer getId() {
			return Id;
		}

		public void setId(Integer id) {
			Id = id;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
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
			Status status1 = (Status) o;
			return Objects.equals(Id, status1.Id) &&
					Objects.equals(status, status1.status) &&
					Objects.equals(descripcion, status1.descripcion) &&
					Objects.equals(proyectoEstudianteSet, status1.proyectoEstudianteSet);
		}

		@Override
		public int hashCode() {
			return Objects.hash(Id, status, descripcion, proyectoEstudianteSet);
		}
	}
