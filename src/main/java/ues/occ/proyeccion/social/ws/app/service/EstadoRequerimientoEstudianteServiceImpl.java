package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.mappers.EstadoRequerimientoEstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class EstadoRequerimientoEstudianteServiceImpl
        extends PageableResource<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO>
        implements EstadoRequerimientoEstudianteService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EstadoRequerimientoEstudianteRepository repository;
    private final EstadoRequerimientoEstudianteMapper mapper;

    @Autowired
    public EstadoRequerimientoEstudianteServiceImpl(EstadoRequerimientoEstudianteRepository repository, EstadoRequerimientoEstudianteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Test purposes constructor
     */
    public EstadoRequerimientoEstudianteServiceImpl(EntityManager entityManager, EstadoRequerimientoEstudianteRepository repository, EstadoRequerimientoEstudianteMapper mapper) {
        this.entityManager = entityManager;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<EstadoRequerimientoEstudianteDTO> findAllByCarnet(int page, int size, String carnet, boolean aprobado) {

        Pageable requerimientoEstudiantePageable = this.getPageable(page, size);
        Page<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes =
                this.repository.findAllByEstudiante_CarnetAndAprobado(carnet, aprobado, requerimientoEstudiantePageable);

        return this.getData(estadoRequerimientoEstudiantes);
    }

    @Override
    public Optional<EstadoRequerimientoEstudianteDTO> save(String carnet, int requerimientoId) {
        carnet = carnet.strip();
        if (carnet.length() != 7) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        try {
            Estudiante estudiante = this.entityManager.getReference(Estudiante.class, carnet);
            Requerimiento requerimiento = this.entityManager.getReference(Requerimiento.class, requerimientoId);
            EstadoRequerimientoEstudiante estadoRequerimientoEstudiante = new EstadoRequerimientoEstudiante(
                    estudiante, requerimiento, true, false,
                    Date.valueOf(LocalDate.now()), null);
            EstadoRequerimientoEstudiante result = repository.save(estadoRequerimientoEstudiante);
            EstadoRequerimientoEstudianteDTO resultDTO = this.mapper.estadoRequerimientoEstudianteToDTO(result);
            return Optional.of(resultDTO);
        }catch (Exception e){
            System.err.println(e);
            return Optional.empty();
        }
    }

    @Override
    protected Function<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO> getMapperFunction() {
        return this.mapper::estadoRequerimientoEstudianteToDTO;
    }
}
