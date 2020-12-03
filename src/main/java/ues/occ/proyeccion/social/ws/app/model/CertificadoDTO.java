package ues.occ.proyeccion.social.ws.app.model;

import lombok.Data;

import java.sql.Date;

@Data
public class CertificadoDTO {
    private int id;
    private String proyecto;
    private String uri;
    private Date fecha_expedicion;
}

