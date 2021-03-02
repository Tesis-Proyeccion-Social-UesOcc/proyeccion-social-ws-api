package ues.occ.proyeccion.social.ws.app.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;

@Data
public class DocumentoDto {

	private Integer Id;
	private String nombre;
	private String descripcion;
	private String uri;
	private LocalDateTime fechaDocumento;
	private Set<Requerimiento> requerimientos;

}
