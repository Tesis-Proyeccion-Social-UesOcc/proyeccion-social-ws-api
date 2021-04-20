package ues.occ.proyeccion.social.ws.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO.CertificadoDTO;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTOUpload;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

public interface CertificadoService {
    Optional<CertificadoCreationDTO.CertificadoDTO> save(CertificadoCreationDTO dto);
    CertificadoCreationDTO.CertificadoDTO getCertificate(String carnet, String projectName);
    PageDtoWrapper<Certificado, CertificadoCreationDTO.CertificadoDTO> findAll(int page, int size);
    PageDtoWrapper<Certificado, CertificadoCreationDTO.CertificadoDTO> findAllByCarnet(int page, int size, String carnet);

    ResponseEntity<ServiceResponse> crearCertificado(int id, MultipartFile file,
                                                     RedirectAttributes redirectAttributes);
    Optional<CertificadoCreationDTO.CertificadoDTO> uploadCertificate(CertificadoCreationDTOUpload dto);

}
