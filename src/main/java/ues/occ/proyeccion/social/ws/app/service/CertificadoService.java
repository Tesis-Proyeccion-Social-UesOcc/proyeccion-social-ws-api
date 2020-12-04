package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;

import java.util.List;
import java.util.Optional;

public interface CertificadoService {
    Optional<CertificadoCreationDTO.CertificadoDTO> save(CertificadoCreationDTO dto);
    List<CertificadoCreationDTO.CertificadoDTO> findAll(int page, int size);
    List<CertificadoCreationDTO.CertificadoDTO> findAllByCarnet(int page, int size, String carnet);

}
