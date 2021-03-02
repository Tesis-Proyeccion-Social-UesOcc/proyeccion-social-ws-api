package ues.occ.proyeccion.social.ws.app.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProcesoDto {

	private Integer Id;
	private String nombre;
	private String descripcion;
	private Date fecha_inicio;
	private Date fecha_fin;
	
}
