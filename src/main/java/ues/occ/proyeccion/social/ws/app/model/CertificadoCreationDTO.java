package ues.occ.proyeccion.social.ws.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificadoCreationDTO{
    @Min(value = 1, message = "ID must be greater then 0")
    private int proyectoId;
    @NotNull(message = "You must provide the certificate url")
    private String uri;

    @Data
    @AllArgsConstructor
    public static class CertificadoDTO {
        private int id;
        private String proyecto;
        private String uri;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime fechaExpedicion;
    }

}

