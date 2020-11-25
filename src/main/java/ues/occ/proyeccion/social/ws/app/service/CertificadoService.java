package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;

import java.net.URL;
import java.util.Optional;

public interface CertificadoService {
    Optional<Certificado> save(String urlCharSequence, int idProyectoEstudiante);
}
