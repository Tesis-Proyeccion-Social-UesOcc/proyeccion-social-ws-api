package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProyectoChangeStatusDto {

	@NotNull(message = "idProyecto no puede ser nulo")
	@Min(value = 1, message = "idProyecto debe ser mayor o igual a 1")
	private Integer idProyecto;
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
}