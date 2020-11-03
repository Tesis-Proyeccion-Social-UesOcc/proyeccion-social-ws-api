package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ProyectoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Status;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;
import ues.occ.proyeccion.social.ws.app.repository.StatusRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ProyectoEstudianteRepository proyectoEstudianteRepository;
    private final StatusRepository statusRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, ProyectoEstudianteRepository proyectoEstudianteRepository, StatusRepository statusRepository) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoEstudianteRepository = proyectoEstudianteRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Proyecto findById(int id) {
       return this.proyectoRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Proyecto> findAll(int page, int size) {
        Pageable paging = this.getPageable(page, size);;
        Page<Proyecto> proyectoPage = proyectoRepository.findAll(paging);
        if(proyectoPage.hasContent()){
            return proyectoPage.getContent();
        }
        else{
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<Proyecto> findAllByStatus(int page, int size, int statusId) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_StatusId(statusId, paging);
        return this.getData(proyectoPage);
    }

    @Override
    public List<Proyecto> findAllPending(int page, int size) {
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_Empty(paging);
        return this.getData(proyectoPage);
    }

    @Override
    public List<Proyecto> findProyectosByEstudiante(int page, int size, String carnet){
        Pageable paging = this.getPageable(page, size);
        Page<Proyecto> proyectoPage = proyectoRepository.findAllByProyectoEstudianteSet_Carnet(carnet, paging);
        return this.getData(proyectoPage);
    }

    private Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }

    private List<Proyecto> getData(Page<Proyecto> proyectos){
        if(proyectos.hasContent()){
            return proyectos.getContent();
        }
        else{
            return Collections.emptyList();
        }
    }

    @Override
    public Proyecto save(Estudiante estudiante, Proyecto proyecto) {
        try {
            Proyecto savedProyecto = this.proyectoRepository.save(proyecto);
            Optional<Status> status = this.statusRepository.findById(1);
            ProyectoEstudiante proyectoEstudiante = new ProyectoEstudiante(
                estudiante, proyecto, status.orElse(new Status(1, "Existing plain status"))
            );
            this.proyectoEstudianteRepository.save(proyectoEstudiante);
            return savedProyecto;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new InternalErrorException("Something went wrong saving the data");
        }
    }
}
