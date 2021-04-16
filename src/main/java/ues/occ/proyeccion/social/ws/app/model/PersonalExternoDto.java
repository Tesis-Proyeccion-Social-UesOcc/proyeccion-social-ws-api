package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalExternoDto {

	private Integer id;
	private String nombre;
	private String apellido;
	private String descripcion;
}
