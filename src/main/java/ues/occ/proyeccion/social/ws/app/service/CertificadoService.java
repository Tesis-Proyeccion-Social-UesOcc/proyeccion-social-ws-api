package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTO;

import java.util.List;
import java.util.Optional;

public interface CertificadoService {
    Optional<Certificado> save(String urlCharSequence, int idProyectoEstudiante);
    List<CertificadoDTO> findAll(int page, int size);
}
