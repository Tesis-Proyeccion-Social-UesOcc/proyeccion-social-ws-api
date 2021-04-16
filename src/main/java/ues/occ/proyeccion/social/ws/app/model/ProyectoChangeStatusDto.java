package ues.occ.proyeccion.social.ws.app.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class ProyectoChangeStatusDto {

	@NotNull(message = "idProyecto no puede estar nulo")
	@Pattern(regexp = "[\\d]+", message = "idProyecto es una variable entera")
	@Positive(message = "idProyecto no puede ser un numero negativo")
	private Integer idProyecto;
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
}