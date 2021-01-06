package ues.occ.proyeccion.social.ws.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;

public interface CertificadoService {
    Optional<CertificadoCreationDTO.CertificadoDTO> save(CertificadoCreationDTO dto);
    Page<CertificadoCreationDTO.CertificadoDTO> findAll(int page, int size);
    Page<CertificadoCreationDTO.CertificadoDTO> findAllByCarnet(int page, int size, String carnet);

    ResponseEntity<ServiceResponse> crearCertificado(int id, MultipartFile file,
                                                     RedirectAttributes redirectAttributes);

}
