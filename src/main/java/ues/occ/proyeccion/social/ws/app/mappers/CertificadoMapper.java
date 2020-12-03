package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTO;

@Mapper
public interface CertificadoMapper {

    @Mapping(source = "certificado.proyectoEstudiante.proyecto.nombre", target = "proyecto")
    CertificadoDTO certificadoToCertificadoDTO(Certificado certificado);
}
