package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requerimiento")
public class Requerimiento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "original", nullable = false, length = 4)
	private Integer original;
	
	@Column(name = "cantidad_copias", nullable = true)
	private Integer cantidad_copias;
	
	@Column(name = "id_proceso", nullable = true)
	private Integer id_proceso;
	
	@Column(name = "id_documento", nullable = true)
	private Integer id_documento;

	public Requerimiento() {
		super();
	}
	
	public Requerimiento(Integer id, Integer original, Integer cantidad_copias, Integer id_proceso,
			Integer id_documento) {
		super();
		this.id = id;
		this.original = original;
		this.cantidad_copias = cantidad_copias;
		this.id_proceso = id_proceso;
		this.id_documento = id_documento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOriginal() {
		return original;
	}

	public void setOriginal(Integer original) {
		this.original = original;
	}

	public Integer getCantidad_copias() {
		return cantidad_copias;
	}

	public void setCantidad_copias(Integer cantidad_copias) {
		this.cantidad_copias = cantidad_copias;
	}

	public Integer getId_proceso() {
		return id_proceso;
	}

	public void setId_proceso(Integer id_proceso) {
		this.id_proceso = id_proceso;
	}

	public Integer getId_documento() {
		return id_documento;
	}

	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}

	@Override
	public String toString() {
		return "Requerimiento [id=" + id + ", original=" + original + ", cantidad_copias=" + cantidad_copias
				+ ", id_proceso=" + id_proceso + ", id_documento=" + id_documento + "]";
	}
	
	
	
}
