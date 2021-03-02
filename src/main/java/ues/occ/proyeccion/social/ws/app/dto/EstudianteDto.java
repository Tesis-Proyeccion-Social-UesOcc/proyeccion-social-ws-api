package ues.occ.proyeccion.social.ws.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class EstudianteDto {
	private String carnet;
	private Integer horasProgreso;
	private boolean servicioCompleto;
	private List<EstadoRequerimientoEstudianteDto> estadoRequerimientoEstudiantes;
	private List<ProyectoEstudianteDto> proyectoEstudianteSet;
}
