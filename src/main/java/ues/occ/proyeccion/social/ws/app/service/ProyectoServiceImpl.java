package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.Status;
import ues.occ.proyeccion.social.ws.app.dao.PersonalExterno;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Personal;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.CycleUtil;
import ues.occ.proyeccion.social.ws.app.mappers.ProyectoMapper;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO.ProyectoDTO;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ProyectoEstudianteRepository proyectoEstudianteRepository;
    private final ProyectoMapper proyectoMapper;
    private static final CycleUtil cycleUtil = new CycleUtil();

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
    public ProyectoDTO findById(int id) {
        return this.proyectoRepository.findById(id)
                .map(proyecto -> this.proyectoMapper.proyectoToProyectoDTO(proyecto, cycleUtil))
                .orElseThrow(ResourceNotFoundException::new);
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
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_StatusId(statusId, paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findAllPending(int page, int size) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_Empty(paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> findProyectosByEstudiante(int page, int size, String carnet, int status) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_Estudiante_CarnetAndProyectoEstudianteSet_Status_id(carnet, status, paging);
        return this.getPagedData(proyectoPage);
    }

    @Override
    public ProyectoCreationDTO.ProyectoDTO save(ProyectoCreationDTO proyecto) {
        try {
            var proyectoToSave = this.proyectoMapper.proyectoCreationDTOToProyecto(proyecto);
            var savedProyecto = this.proyectoRepository.save(proyectoToSave);
            var proyectoEstudianteList = proyecto.getEstudiantes().stream()
                    .map(carnet -> this.entityManager.getReference(Estudiante.class, carnet))
                    .map(estudiante -> new ProyectoEstudiante(estudiante, savedProyecto))
                    .collect(Collectors.toList());
            this.proyectoEstudianteRepository.saveAll(proyectoEstudianteList);
            return this.proyectoMapper.proyectoToProyectoDTO(savedProyecto, cycleUtil);
        } catch (Exception e) {
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

    private PageDtoWrapper<Proyecto, ProyectoDTO> getPagedData(Page<Proyecto> data) {
        List<ProyectoDTO> content;
        if (data.hasContent()) {
            content = data.getContent().stream()
                    .map(proyecto -> this.proyectoMapper.proyectoToProyectoDTO(proyecto, cycleUtil))
                    .collect(Collectors.toList());
        } else {
            content = Collections.emptyList();
        }
        return new PageDtoWrapper<>(data, content);
    }

    private Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }
}
