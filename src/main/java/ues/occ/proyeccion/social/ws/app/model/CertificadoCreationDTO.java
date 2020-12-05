package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
public class CertificadoCreationDTO{
    @Size(min = 1)
    @NotNull(message = "You must provide project id")
    private int proyectoId;
    @NotNull(message = "You must provide the certificate url")
    private String uri;

    @Data
    public static class CertificadoDTO {
        private int id;
        private String proyecto;
        private String uri;
        private Date fecha_expedicion;
    }

}

