package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "duracion", nullable = false)
	private Integer duracion;
	
	@Column(name = "interno", nullable = false, length = 4)
	private boolean interno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tutor")
	private Personal tutor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_encargado_externo")
	private PersonalExterno encargadoExterno;

	
	public Proyecto() {
		super();
	}


}
