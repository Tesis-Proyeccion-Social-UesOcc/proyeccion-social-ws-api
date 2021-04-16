package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class DocumentoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;

	private MultipartFile file;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "DocumentoRequest [nombre=" + nombre + ", file=" + file + "]";
	}

}
