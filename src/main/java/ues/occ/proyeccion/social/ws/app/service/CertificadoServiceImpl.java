package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class CertificadoServiceImpl extends PageableResource<Certificado, CertificadoDTO> implements CertificadoService {
    @PersistenceContext
    EntityManager entityManager;

    private final CertificadoRepository certificadoRepository;
    private final CertificadoMapper certificadoMapper;
    private final static Logger LOGGER = Logger.getLogger(CertificadoServiceImpl.class.getName());

    public CertificadoServiceImpl(CertificadoRepository certificadoRepository, CertificadoMapper certificadoMapper) {
        this.certificadoRepository = certificadoRepository;
        this.certificadoMapper = certificadoMapper;
    }

    @Override
    public Optional<Certificado> save(String urlCharSequence, int idProyectoEstudiante) {
        try{
            URL url = new URL(urlCharSequence);
            ProyectoEstudiante proyectoEstudiante = this.entityManager.getReference(ProyectoEstudiante.class, idProyectoEstudiante);
            Certificado certificado = new Certificado(idProyectoEstudiante, url.toString(), Date.valueOf(LocalDate.now()), proyectoEstudiante);
            Certificado result = this.certificadoRepository.save(certificado);
            return Optional.of(result);
        }
        catch (MalformedURLException e){
            LOGGER.warning(e.getMessage());
            throw new IllegalArgumentException("There was a mistake in the url format");
        }
        catch (Exception e){
            LOGGER.warning(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<CertificadoDTO> findAll(int page, int size) {
        Pageable pageable = this.getPageable(page, size);
        Page<Certificado> certificadoPage = this.certificadoRepository.findAll(pageable);
        return this.getData(certificadoPage);
    }

    @Override
    protected Function<Certificado, CertificadoDTO> getMapperFunction() {
        return this.certificadoMapper::certificadoToCertificadoDTO;
    }
}
