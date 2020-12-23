package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class CertificadoServiceImpl extends PageableResource<Certificado, CertificadoCreationDTO.CertificadoDTO> implements CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final ProyectoEstudianteRepository proyectoEstudianteRepository;
    private final CertificadoMapper certificadoMapper;
    private final static Logger LOGGER = Logger.getLogger(CertificadoServiceImpl.class.getName());

    public CertificadoServiceImpl(CertificadoRepository certificadoRepository,
                                  ProyectoEstudianteRepository proyectoEstudianteRepository,
                                  CertificadoMapper certificadoMapper) {
        this.certificadoRepository = certificadoRepository;
        this.proyectoEstudianteRepository = proyectoEstudianteRepository;
        this.certificadoMapper = certificadoMapper;
    }

    @Override
    public Optional<CertificadoCreationDTO.CertificadoDTO> save(CertificadoCreationDTO dto) {
        ProyectoEstudiante proyectoEstudiante =
                this.proyectoEstudianteRepository.findByProyecto_Id(dto.getProyectoId())
                .orElseThrow(() -> new ResourceNotFoundException("Project does not exists or is not assigned yet"));
        try{
            URL url = new URL(dto.getUri());
            Certificado certificado = new Certificado(proyectoEstudiante.getId(), url.toString(), Date.valueOf(LocalDate.now()), proyectoEstudiante);
            Certificado result = this.certificadoRepository.save(certificado);
            return Optional.of(this.certificadoMapper.certificadoToCertificadoDTO(result));
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
    public List<CertificadoCreationDTO.CertificadoDTO> findAll(int page, int size) {
        Pageable pageable = this.getPageable(page, size);
        Page<Certificado> certificadoPage = this.certificadoRepository.findAll(pageable);
        return this.getData(certificadoPage);
    }

    @Override
    public List<CertificadoCreationDTO.CertificadoDTO> findAllByCarnet(int page, int size, String carnet) {
        Pageable pageable = this.getPageable(page, size);
        Page<Certificado> certificadoPage = this.certificadoRepository.findAllByProyectoEstudiante_Estudiante_Carnet(carnet, pageable);
        return this.getData(certificadoPage);
    }

    @Override
    protected Function<Certificado, CertificadoCreationDTO.CertificadoDTO> getMapperFunction() {
        return this.certificadoMapper::certificadoToCertificadoDTO;
    }
}
