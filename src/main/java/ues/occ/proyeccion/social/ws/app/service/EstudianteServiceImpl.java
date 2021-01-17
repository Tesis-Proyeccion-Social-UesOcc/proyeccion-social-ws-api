package ues.occ.proyeccion.social.ws.app.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.EstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

@Service
public class EstudianteServiceImpl extends PageableResource<Estudiante, EstudianteDTO> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper mapper;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, EstudianteMapper mapper) {
        this.estudianteRepository = estudianteRepository;
        this.mapper = mapper;
    }

    @Override
    public PageDtoWrapper<Estudiante, EstudianteDTO> findAllByServicio(int page, int size, boolean isComplete) {
        Pageable pageable = this.getPageable(page, size);
        Page<Estudiante> estudiantePage = this.estudianteRepository.findAllByServicioCompleto(pageable, isComplete);
        return this.getPagedData(estudiantePage);
    }

    @Override
    public EstudianteDTO findByCarnet(String carnet) {
        carnet = carnet.strip();
        if (carnet.length() != 7) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        Optional<Estudiante> estudiante = this.estudianteRepository.findByCarnetIgnoreCase(carnet);
        return estudiante.map(this.mapper::estudianteToEstudianteDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student with the given ID does not exists"));
    }

    @Override
    protected Function<Estudiante, EstudianteDTO> getMapperFunction() {
        return this.mapper::estudianteToEstudianteDTO;
    }
}
