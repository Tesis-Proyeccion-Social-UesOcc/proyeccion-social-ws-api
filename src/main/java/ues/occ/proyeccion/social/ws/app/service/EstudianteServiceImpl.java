package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public List<Estudiante> findAllByServicio(int page, int size, boolean isComplete) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Estudiante> estudiantePage = this.estudianteRepository.findAllByServicio_completo(pageable, isComplete);
        if (estudiantePage.hasContent()) {
            return estudiantePage.getContent();
        } else
            return Collections.emptyList();
    }

    @Override
    public Estudiante findByCarnet(String carnet) {
        carnet = carnet.strip();
        if (carnet.length() != 7) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        Optional<Estudiante> estudiante = this.estudianteRepository.findByCarnetIgnoreCase(carnet);
        return estudiante.orElseThrow(ResourceNotFoundException::new);
    }
}
