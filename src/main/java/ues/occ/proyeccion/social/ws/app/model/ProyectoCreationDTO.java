package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoCreationDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
    private Integer duracion;
    private boolean interno;
    private int personal;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyectoDTO implements Serializable{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String nombre;
        private Integer duracion;
        private boolean interno;
        private String personal;
    }
}


