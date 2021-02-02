package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoCreationDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
    private Integer duracion;
    private boolean interno;
    private int personal;

    @Data
    @Accessors(chain = true)
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyectoDTO implements Serializable{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
        private Integer id;
        private String nombre;
        private Integer duracion;
        private boolean interno;
        private String personal;
        private Set<EstudianteDTO> estudiantes;
    }
}


