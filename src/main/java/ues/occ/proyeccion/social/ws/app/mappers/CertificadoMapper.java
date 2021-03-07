package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;

@Mapper
public interface CertificadoMapper {
    public static CertificadoMapper INSTANCE = Mappers.getMapper(CertificadoMapper.class);

    @Mapping(source = "certificado.proyecto.nombre", target = "proyecto")
    CertificadoCreationDTO.CertificadoDTO certificadoToCertificadoDTO(Certificado certificado);
}
