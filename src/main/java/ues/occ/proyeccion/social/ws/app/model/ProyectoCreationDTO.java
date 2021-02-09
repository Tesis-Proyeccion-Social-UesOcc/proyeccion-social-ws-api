package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ues.occ.proyeccion.social.ws.app.validators.CarnetValidator;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoCreationDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @NotBlank(message = "You need to specify a valid project name")
	private String nombre;

    @NotNull(message = "Mandatory param")
	@Min(value = 100, message = "Minimum is 100 hours")
    @Max(value = 500, message = "Maximum is 500 hours")
    private Integer duracion;

    @NotNull(message = "Mandatory param")
    private boolean interno;

    @NotNull(message = "Mandatory param")
    @Positive(message = "Personal param must to be a valid ID")
    private int personal;

    @NotEmpty(message = "Estudiantes list cannot be empty")
    private List<@CarnetValidator String> estudiantes;

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


