package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;

@Mapper
public interface CertificadoMapper {

    @Mapping(source = "certificado.proyectoEstudiante.proyecto.nombre", target = "proyecto")
    CertificadoCreationDTO.CertificadoDTO certificadoToCertificadoDTO(Certificado certificado);
}
