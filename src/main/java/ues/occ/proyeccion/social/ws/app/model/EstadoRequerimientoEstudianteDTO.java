package ues.occ.proyeccion.social.ws.app.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class EstadoRequerimientoEstudianteDTO{
    private boolean aprobado;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaEntrega;

}
