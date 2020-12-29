package ues.occ.proyeccion.social.ws.app.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class DocumentoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String nombre;

	private String descripcion;

	private MultipartFile file;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "DocumentoRequest [nombre=" + nombre + ", descripcion=" + descripcion + ", file=" + file.getOriginalFilename() + "]";
	}
	
}
