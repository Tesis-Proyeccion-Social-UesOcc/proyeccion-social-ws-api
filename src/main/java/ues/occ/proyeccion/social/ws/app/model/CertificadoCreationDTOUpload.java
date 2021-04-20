package ues.occ.proyeccion.social.ws.app.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificadoCreationDTOUpload {
    @Min(value = 1, message = "ID must be greater then 0")
    private int proyectoEstudianteId;
    //@NotNull(message = "You must provide the certificate url")
    private MultipartFile file;

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

