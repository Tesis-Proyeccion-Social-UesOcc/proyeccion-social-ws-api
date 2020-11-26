package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;

import java.util.List;
import java.util.Optional;

public interface CertificadoService {
    Optional<Certificado> save(String urlCharSequence, int idProyectoEstudiante);
    List<Certificado> findAll(int page, int size);
}
