package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_departamento", referencedColumnName = "id")
	private Departamento departamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_personal", referencedColumnName = "id")
	private TipoPersonal tipoPersonal;

	@OneToMany(mappedBy = "tutor", fetch = FetchType.LAZY)
	private Set<Proyecto> proyectos;

	// TODO: 9/8/20 Add one-to-one between this and personal_encargado


	public Personal() {
		super();
	}


}
