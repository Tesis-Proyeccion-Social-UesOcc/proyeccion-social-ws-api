package ues.occ.proyeccion.social.ws.app.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificadoCreationDTO{
    @Min(value = 1, message = "ID must be greater then 0")
    private int proyectoEstudianteId;
    @NotNull(message = "You must provide the certificate url")
    private String uri;

    @Data
    @AllArgsConstructor
    public static class CertificadoDTO {
        private int proyectoEstudianteId;
        private String proyecto;
        private String uri;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime fechaExpedicion;
    }

}

