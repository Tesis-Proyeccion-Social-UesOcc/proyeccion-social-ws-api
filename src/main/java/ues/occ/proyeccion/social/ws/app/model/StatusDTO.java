package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
	private Integer id;
	private String status;
	private String descripcion;
	
	public StatusDTO(Integer id) {
		this.id = id;
	}
}
