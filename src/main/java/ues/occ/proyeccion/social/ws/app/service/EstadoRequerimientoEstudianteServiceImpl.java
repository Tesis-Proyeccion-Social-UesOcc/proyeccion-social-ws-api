package ues.occ.proyeccion.social.ws.app.service;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.mappers.EstadoRequerimientoEstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EstadoRequerimientoEstudianteServiceImpl implements EstadoRequerimientoEstudianteService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EstadoRequerimientoEstudianteRepository repository;
    private final EstadoRequerimientoEstudianteMapper mapper;

    public EstadoRequerimientoEstudianteServiceImpl(EstadoRequerimientoEstudianteRepository repository, EstadoRequerimientoEstudianteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // todo: refactor repositories to add entityManager and getSession method, that's needed to use only the entities ID in insertions, otherwise findById would be used
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
}