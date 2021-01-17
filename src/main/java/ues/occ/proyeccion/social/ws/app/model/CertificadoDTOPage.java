package ues.occ.proyeccion.social.ws.app.model;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertificadoDTOPage {
    private Page<CertificadoCreationDTO.CertificadoDTO> certificados;
}
