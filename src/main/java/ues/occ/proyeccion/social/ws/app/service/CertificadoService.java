package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;

import java.util.List;
import java.util.Optional;

public interface CertificadoService {
    Optional<CertificadoCreationDTO.CertificadoDTO> save(CertificadoCreationDTO dto);
    List<CertificadoCreationDTO.CertificadoDTO> findAll(int page, int size);
    List<CertificadoCreationDTO.CertificadoDTO> findAllByCarnet(int page, int size, String carnet);

    ResponseEntity<ServiceResponse> crearCertificado(int id, MultipartFile file,
                                                     RedirectAttributes redirectAttributes);

}
