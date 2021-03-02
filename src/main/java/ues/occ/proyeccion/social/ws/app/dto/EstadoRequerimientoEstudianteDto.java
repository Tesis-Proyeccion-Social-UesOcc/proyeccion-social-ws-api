package ues.occ.proyeccion.social.ws.app.dto;

import java.sql.Date;

import lombok.Data;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiantePK;

@Data
public class EstadoRequerimientoEstudianteDto {

	private EstadoRequerimientoEstudiantePK id;
	private RequerimientoDto requerimiento;
	private boolean entregado;
	private boolean aprobado;
	private Date fechaEntrega;
	private Date fechaAprobacion;
}
