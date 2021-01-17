package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadoRequerimientoEstudianteDTOList implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EstadoRequerimientoEstudianteDTO> documentos;
}
