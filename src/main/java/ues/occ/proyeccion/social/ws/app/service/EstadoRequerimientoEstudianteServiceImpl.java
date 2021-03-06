package ues.occ.proyeccion.social.ws.app.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.mappers.EstadoRequerimientoEstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

@Service
public class EstadoRequerimientoEstudianteServiceImpl
		extends PageableResource<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO>
		implements EstadoRequerimientoEstudianteService {

	private static final Logger log = LoggerFactory.getLogger(EstadoRequerimientoEstudianteServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	private final EstadoRequerimientoEstudianteRepository repository;
	private final EstadoRequerimientoEstudianteMapper mapper;

	@Autowired
	public EstadoRequerimientoEstudianteServiceImpl(EstadoRequerimientoEstudianteRepository repository,
			EstadoRequerimientoEstudianteMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	/**
	 * Test purposes constructor
	 */
	public EstadoRequerimientoEstudianteServiceImpl(EntityManager entityManager,
			EstadoRequerimientoEstudianteRepository repository, EstadoRequerimientoEstudianteMapper mapper) {
		this.entityManager = entityManager;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public PageDtoWrapper<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO> findAllByCarnet(int page,
			int size, String carnet, boolean aprobado) {

		Pageable requerimientoEstudiantePageable = this.getPageable(page, size);
		Page<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes = this.repository
				.findAllByProyectoEstudiante_Estudiante_CarnetAndAprobado(carnet, aprobado, requerimientoEstudiantePageable);

		return this.getPagedData(estadoRequerimientoEstudiantes);
	}

	@Override
	public PageDtoWrapper<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO> findAllByCarnet(int page,
			int size, String carnet) {

		Pageable requerimientoEstudiantePageable = this.getPageable(page, size);
		Page<EstadoRequerimientoEstudiante> estadoRequerimientoEstudiantes = this.repository
				.findAllByProyectoEstudiante_Estudiante_Carnet(carnet, requerimientoEstudiantePageable);

		return this.getPagedData(estadoRequerimientoEstudiantes);
	}

	@Override
	public Optional<EstadoRequerimientoEstudianteDTO> save(Integer idProyectoEstudiante, int requerimientoId) {
		log.info("cambio de estado de requerimiento, idProyectoEstudiante: " + idProyectoEstudiante + " requerimientoId: " + requerimientoId);

		try {
			var proyectoEstudiante = this.entityManager.getReference(ProyectoEstudiante.class, idProyectoEstudiante);
			Requerimiento requerimiento = this.entityManager.getReference(Requerimiento.class, requerimientoId);
			// verificar si estado entregado o aprobado
			EstadoRequerimientoEstudiante estadoRequerimientoEstudiante = 
					repository.findByProyectoEstudiante_IdAndEntregadoAndRequerimientoId(idProyectoEstudiante, true, requerimientoId);
			
			if (estadoRequerimientoEstudiante != null) {
						estadoRequerimientoEstudiante.setAprobado(true);
						estadoRequerimientoEstudiante.setFechaAprobacion(Date.valueOf(LocalDate.now()));
			} else {
				 estadoRequerimientoEstudiante = new EstadoRequerimientoEstudiante(
						proyectoEstudiante, requerimiento, true, false, Date.valueOf(LocalDate.now()), null);
			}

			EstadoRequerimientoEstudiante result = repository.save(estadoRequerimientoEstudiante);
			EstadoRequerimientoEstudianteDTO resultDTO = this.mapper.estadoRequerimientoEstudianteToDTO(result);
			return Optional.of(resultDTO);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	protected Function<EstadoRequerimientoEstudiante, EstadoRequerimientoEstudianteDTO> getMapperFunction() {
		return this.mapper::estadoRequerimientoEstudianteToDTO;
	}

}
