package ues.occ.proyeccion.social.ws.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.*;
import ues.occ.proyeccion.social.ws.app.model.ProyectoChangeStatusDto;
import ues.occ.proyeccion.social.ws.app.model.StatusEnum;
import ues.occ.proyeccion.social.ws.app.exceptions.ChangeStatusProjectException;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.CycleUtil;
import ues.occ.proyeccion.social.ws.app.mappers.ProyectoMapper;
import ues.occ.proyeccion.social.ws.app.model.PendingProjectDTO;
import ues.occ.proyeccion.social.ws.app.model.ProjectMarker;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO.ProyectoDTO;
import ues.occ.proyeccion.social.ws.app.repository.DocumentoRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;
import ues.occ.proyeccion.social.ws.app.repository.RequerimientoRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.StatusOption;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final DocumentoRepository documentoRepository;
    private final ProyectoMapper proyectoMapper;
    private final EstudianteRepository estudianteRepository;
    private final RequerimientoRepository requerimientoRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, DocumentoRepository documentoRepository, ProyectoMapper proyectoMapper,
                               EstudianteRepository estudianteRepository, RequerimientoRepository requerimientoRepository, EntityManager entityManager) {
        this.proyectoRepository = proyectoRepository;
        this.documentoRepository = documentoRepository;
        this.proyectoMapper = proyectoMapper;
        this.estudianteRepository = estudianteRepository;
        this.requerimientoRepository = requerimientoRepository;
        this.entityManager = entityManager;
    }

    @Override
    public ProyectoDTO findById(int id) {
        return this.proyectoRepository.findById(id)
                .map(proyecto -> this.proyectoMapper.proyectoToProyectoDTO(proyecto, new CycleUtil<>()))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProjectMarker findByCarnetAndProjectName(String carnet, String projectName) {
        return this.proyectoRepository.findByProyectoEstudianteSet_Estudiante_CarnetIgnoreCaseAndNombreIgnoreCase(carnet, projectName)
                .map(proyecto -> {
                    if(proyecto.getStatus().getId() == StatusOption.PENDIENTE){
                        var docs = this.documentoRepository.findProjectRelatedDocuments(carnet, proyecto.getNombre());
                        return proyectoMapper.mapToPendingProject(proyecto, docs, new CycleUtil<>());
                    }
                    else {
                        return proyectoMapper.proyectoToProyectoDTO(proyecto, new CycleUtil<>());
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Proyecto no encontrado, asegurese de ingresar correctamente su carnet y el nombre del proyecto"));
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoDTO> findAll(int page, int size) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAll(paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllByStatus(int page, int size, int statusId) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByStatus_Id(statusId, paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllPending(int page, int size) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByStatus_Id(StatusOption.PENDIENTE, paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository
                .findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(status, carnet, paging);

        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, PendingProjectDTO> findProyectosPendientesByEstudiante(int page, int size, String carnet, int status) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository
                .findAllByStatus_IdAndProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(status, carnet, paging);

        return this.getPagedData(proyectoPage, carnet);
    }

    @Override
    public List<? extends ProjectMarker> getRequirementsData(String carnet) {
        return this.proyectoRepository.findAllByProyectoEstudianteSet_Estudiante_CarnetIgnoreCase(carnet)
                .stream().map(proyecto -> {
                    if(proyecto.getStatus().getId() == StatusOption.PENDIENTE){
                        var docs = this.documentoRepository.findProjectRelatedDocuments(carnet, proyecto.getNombre());
                        return this.proyectoMapper.mapToPendingProject(proyecto, docs, new CycleUtil<>());
                    }
                    else {
                        return this.proyectoMapper.proyectoToProyectoDTO(proyecto, new CycleUtil<>());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProyectoCreationDTO.ProyectoDTO save(ProyectoCreationDTO proyecto) {
        try {

            var proyectoToSave = this.proyectoMapper.proyectoCreationDTOToProyecto(proyecto);
            ZoneId zid = ZoneId.of("America/Guatemala");
            proyectoToSave.setFechaCreacion(LocalDateTime.now(zid));
            proyectoToSave.setStatus(entityManager.getReference(Status.class, 1));
            this.setEncargado(proyectoToSave, proyecto.getPersonal());

            proyecto.getEstudiantes().forEach(carnet -> {
                var studentProxy = this.entityManager.getReference(Estudiante.class, carnet);
                proyectoToSave.registerStudent(studentProxy);
            });

            var savedProyecto = this.proyectoRepository.save(proyectoToSave);
            var cache = new ArrayList<Requerimiento>();
            savedProyecto.getProyectoEstudianteSet().forEach(proyectoEstudiante -> {
                this.requerimientoRepository.findAllProjectedBy()
                        .forEach(requerimientoIdView -> {
                            var requerimiento = entityManager.getReference(Requerimiento.class, requerimientoIdView.getId());
                            requerimiento.addEstadoRequerimiento(proyectoEstudiante, false);
                            cache.add(requerimiento);
                        });
            });
            this.requerimientoRepository.saveAll(cache);
            return this.proyectoMapper.proyectoToProyectoDTO(savedProyecto, new CycleUtil<>());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrorException("Something went wrong saving the data");
        }

    }

    @Override
    public ProyectoDTO update(ProyectoCreationDTO proyecto, int idProyecto) {
        try{
            // required or get a compile error, atomic variables are thread-safe variables
            AtomicBoolean toInactive = new AtomicBoolean(false);
            var proyectoDB = this.proyectoRepository.findById(idProyecto)
                    .map(obj -> {
                        obj.setDuracion(proyecto.getDuracion());
                        obj.setInterno(proyecto.isInterno());
                        obj.setNombre(proyecto.getNombre());
                        obj.getProyectoEstudianteSet().forEach(proyectoEstudiante -> {

                            for(var carnet: proyecto.getEstudiantes()){
                                if(!proyectoEstudiante.getEstudiante().getCarnet().equals(carnet)) {
                                    // if this statement is reached means that this student exist in the db but was not found in the body of the PUT request , which means it will be set as inactive
                                    toInactive.set(true);
                                }
                                else{
                                        toInactive.set(false);
                                        break;
                                }
                            }
                            var exists = toInactive.getAndSet(false);
                            proyectoEstudiante.setActive(!exists);
                        });

                        return obj;
                    }).orElseThrow(() -> new ResourceNotFoundException(String.format("Project with id %d does not exist", idProyecto)));
            this.setEncargado(proyectoDB, proyecto.getPersonal());
            var savedProyecto = this.proyectoRepository.save(proyectoDB);
            return this.proyectoMapper.proyectoToProyectoDTO(savedProyecto, new CycleUtil<>());
        }
        catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new InternalErrorException("Something went wrong saving the data");
        }

    }

    private void setEncargado(Proyecto proyecto, int idPersonal) {
        if (proyecto.isInterno()) {
            Personal personal = this.entityManager.getReference(Personal.class, idPersonal);
            proyecto.setTutor(personal);
        } else {
            PersonalExterno personalExterno = this.entityManager.getReference(PersonalExterno.class, idPersonal);
            proyecto.setEncargadoExterno(personalExterno);
        }
    }

    private PageDtoWrapper<Proyecto, PendingProjectDTO> getPagedData(Page<Proyecto> data, String carnet) {
        List<PendingProjectDTO> content;
        if (data.hasContent()) {
            content = data.getContent().stream()
                    .map(proyecto -> {
                        var docs = this.documentoRepository.findProjectRelatedDocuments(carnet, proyecto.getNombre());
                        return this.proyectoMapper.mapToPendingProject(proyecto, docs, new CycleUtil<>());
                    })
                    .collect(Collectors.toList());
        } else {
            content = Collections.emptyList();
        }
        return new PageDtoWrapper<>(data, content);
    }


    private PageDtoWrapper<Proyecto, ProyectoDTO> getPagedData(Page<Proyecto> data) {
        List<ProyectoDTO> content;
        if (data.hasContent()) {
            content = data.getContent().stream()
                    .map(proyecto -> this.proyectoMapper.proyectoToProyectoDTO(proyecto, new CycleUtil()))
                    .collect(Collectors.toList());
        } else {
            content = Collections.emptyList();
        }
        return new PageDtoWrapper<>(data, content);
    }

    private Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
	public ProyectoDTO changeStatus(ProyectoChangeStatusDto proyectoDto) {

		Proyecto proyecto = proyectoRepository.findById(proyectoDto.getIdProyecto())
				.orElseThrow(() -> new ChangeStatusProjectException(
						"No se encontro el proyecto con id: " + proyectoDto.getIdProyecto()));

		var proyectoStatus = this.proyectoMapper.proyectoToProyectoDTO(proyecto, new CycleUtil<>());

		if (proyecto.getStatus().getStatus().equalsIgnoreCase("Pendiente")) {
			if (proyectoDto.getStatus() == StatusEnum.PENDIENTE) {
				throw new ChangeStatusProjectException(
						"El proyecto ya esta en estado pendiente. Podria  asignar estado rechazado o en proceso");
			} else if (proyectoDto.getStatus() == StatusEnum.COMPLETADO) {
				throw new ChangeStatusProjectException(
						"No se puede asignar el estado completado, porque el proyecto esta pendiente. Podria asignar estado rechazado o en proceso porfavor");
			}
		}

		if (proyecto.getStatus().getStatus().equalsIgnoreCase("En proceso")) {
			if (proyectoDto.getStatus() == StatusEnum.PENDIENTE) {
				throw new ChangeStatusProjectException(
						"El proyecto ya esta en proceso, no se puede asginar el estado pendiente. Podria asignar estado completado o retiro porfavor");
			} else if (proyectoDto.getStatus() == StatusEnum.RECHAZADO) {
				throw new ChangeStatusProjectException(
						"El proyecto ya esta en proceso, no se puede rechazar, solamente retirar");
			}
		}

		if (proyecto.getStatus().getStatus().equalsIgnoreCase("Completado")
                || proyecto.getStatus().getStatus().equalsIgnoreCase("Retiro")
				|| proyecto.getStatus().getStatus().equalsIgnoreCase("Rechazado")) {
			throw new ChangeStatusProjectException("Estado de proyecto: " + proyecto.getStatus().getStatus()
					+ ", el proyecto ya esta en un estado definitivo, este no se puede cambiar.");
		}

		int horaProyectoActual = proyecto.getDuracion();

		if(proyecto.getStatus().getStatus().equalsIgnoreCase("En proceso") && proyectoDto.getStatus() == StatusEnum.COMPLETADO) {
			proyecto.getProyectoEstudianteSet().forEach(estudianteProyecto -> {
				int horasEstudiante = estudianteProyecto.getEstudiante().getHorasProgreso();
				int totalHorasEstudiante = horaProyectoActual + horasEstudiante;
				estudianteProyecto.getEstudiante().setHorasProgreso(totalHorasEstudiante);
				if(totalHorasEstudiante>=300) {
					estudianteProyecto.getEstudiante().setServicioCompleto(true);
					estudianteRepository.save(estudianteProyecto.getEstudiante());
				}
			});
		}

		proyecto.setStatus(new Status(proyectoDto.getStatus().ordinal() + 1));
		ZoneId zid = ZoneId.of("America/Guatemala");
		proyecto.setFechaModificacion(LocalDateTime.now(zid));
		proyecto = proyectoRepository.save(proyecto);
		return this.proyectoMapper.proyectoToProyectoDTO(proyecto, new CycleUtil());
	}
}

