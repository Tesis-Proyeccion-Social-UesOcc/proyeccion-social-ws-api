package ues.occ.proyeccion.social.ws.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ues.occ.proyeccion.social.ws.app.dao.Status;
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

    private StatusDTO status;

    @Data
    @Accessors(chain = true)
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyectoDTO implements Serializable, ProjectMarker{
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
        private LocalDateTime fechaCreacion;
        private LocalDateTime fechaModificacion;
        private String status;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProyectoDTO that = (ProyectoDTO) o;

            // to avoid NPE when fechaCreacion or fechaModificacion are null
            var fechaCreacionValue = fechaCreacion == null ? null : fechaCreacion.truncatedTo(ChronoUnit.SECONDS);
            var thatFechaCreacionValue = that.fechaCreacion == null ? null : that.fechaCreacion.truncatedTo(ChronoUnit.SECONDS);
            var fechaModificacionValue = fechaModificacion == null ? null : fechaModificacion.truncatedTo(ChronoUnit.SECONDS);
            var thatFechaModificacionValue = that.fechaModificacion == null ? null : that.fechaModificacion.truncatedTo(ChronoUnit.SECONDS);

            return interno == that.interno && Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre)
                    && Objects.equals(duracion, that.duracion) && Objects.equals(personal, that.personal)
                    && Objects.equals(estudiantes, that.estudiantes) && Objects.equals(fechaCreacionValue, thatFechaCreacionValue)
                    && Objects.equals(fechaModificacionValue, thatFechaModificacionValue)
                    && Objects.equals(status, that.status);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, nombre, duracion, interno, personal, estudiantes, fechaCreacion, fechaModificacion, status);
        }
    }
}


