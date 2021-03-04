package ues.occ.proyeccion.social.ws.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonalEncargadoDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String departamento;
    private String horario;
    private String ubicacion;
}
