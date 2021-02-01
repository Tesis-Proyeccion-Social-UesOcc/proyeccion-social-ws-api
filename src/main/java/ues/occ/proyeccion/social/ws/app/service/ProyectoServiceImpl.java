package ues.occ.proyeccion.social.ws.app.service;

import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.dao.PersonalExterno;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Status;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.ProyectoMapper;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO.ProyectoDTO;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

@Service
public class ProyectoServiceImpl extends PageableResource<Proyecto, ProyectoCreationDTO.ProyectoDTO> implements ProyectoService{

    private final ProyectoRepository proyectoRepository;
    private final ProyectoEstudianteRepository proyectoEstudianteRepository;
    private final ProyectoMapper proyectoMapper;

	private static final Logger log = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, ProyectoEstudianteRepository proyectoEstudianteRepository,
                               ProyectoMapper proyectoMapper) {

        this.proyectoRepository = proyectoRepository;
        this.proyectoEstudianteRepository = proyectoEstudianteRepository;
        this.proyectoMapper = proyectoMapper;
    }

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, ProyectoEstudianteRepository proyectoEstudianteRepository,
                               ProyectoMapper proyectoMapper, EntityManager entityManager) {

        this.proyectoRepository = proyectoRepository;
        this.proyectoEstudianteRepository = proyectoEstudianteRepository;
        this.proyectoMapper = proyectoMapper;
        this.entityManager = entityManager;
    }

    @Override
    public Proyecto findById(int id) {
       return this.proyectoRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoDTO> findAll(int page, int size) {
        Pageable paging = this.getPageable(page, size);;
        Page<Proyecto> proyectoPage = proyectoRepository.findAll(paging);
        log.info(proyectoPage.getContent().toString());
        return this.getPagedData(proyectoPage);
    }

	@Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllByStatus(int page, int size, int statusId) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_StatusId(statusId, paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO>findAllPending(int page, int size) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_Empty(paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status){
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_Estudiante_CarnetAndProyectoEstudianteSet_Status_id(carnet, status, paging);
    return this.getPagedData(proyectoPage);
    }

    @Override
    public ProyectoCreationDTO save(String carnet, ProyectoCreationDTO proyecto) {
        try {

            Proyecto proyectoToSave = this.proyectoMapper.proyectoCreationDTOToProyecto(proyecto);
            Estudiante estudiante = this.entityManager.getReference(Estudiante.class, carnet);
            this.setEncargado(proyectoToSave, proyecto.getPersonal());
            Proyecto savedProyecto = this.proyectoRepository.save(proyectoToSave);
            Status status = this.entityManager.getReference(Status.class, 1);
            ProyectoEstudiante proyectoEstudiante = new ProyectoEstudiante(
                estudiante, savedProyecto, status
            );
            this.proyectoEstudianteRepository.save(proyectoEstudiante);
            return this.proyectoMapper.proyectoToProyectoCreationDTO(savedProyecto);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new InternalErrorException("Something went wrong saving the data");
        }
    }

    private void setEncargado(Proyecto proyecto, int idPersonal){
        if(proyecto.isInterno()){
            Personal personal = this.entityManager.getReference(Personal.class, idPersonal);
            proyecto.setTutor(personal);
        }
        else{
            PersonalExterno personalExterno = this.entityManager.getReference(PersonalExterno.class, idPersonal);
            proyecto.setEncargadoExterno(personalExterno);
        }
    }

    @Override
    protected Function<Proyecto, ProyectoCreationDTO.ProyectoDTO> getMapperFunction() {
        return this.proyectoMapper::proyectoToProyectoDTO;
    }
}
