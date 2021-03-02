package ues.occ.proyeccion.social.ws.app.dto;

import lombok.Data;

@Data
public class RequerimientoDto {

    private Integer id;
    private boolean original;
    private Integer cantidadCopias;
    private ProcesoDto proceso;
	private DocumentoDto documento;

}
