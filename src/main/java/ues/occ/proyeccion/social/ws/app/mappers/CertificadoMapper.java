package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;

@Mapper
public interface CertificadoMapper {
    public static CertificadoMapper INSTANCE = Mappers.getMapper(CertificadoMapper.class);

    @Mapping(source = "certificado.proyectoEstudiante.proyecto.nombre", target = "proyecto")
    @Mapping(source = "id", target = "proyectoEstudianteId")
    CertificadoCreationDTO.CertificadoDTO certificadoToCertificadoDTO(Certificado certificado);
}
