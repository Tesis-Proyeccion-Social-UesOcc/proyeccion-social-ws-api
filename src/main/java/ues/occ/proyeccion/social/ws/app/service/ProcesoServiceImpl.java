package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.mappers.ProcesoMapper;
import ues.occ.proyeccion.social.ws.app.model.ProcesoDTO;
import ues.occ.proyeccion.social.ws.app.repository.ProcesoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcesoServiceImpl implements ProcesoService {

    private final ProcesoRepository procesoRepository;
    private final ProcesoMapper mapper;

    public ProcesoServiceImpl(ProcesoRepository procesoRepository, ProcesoMapper mapper) {
        this.procesoRepository = procesoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProcesoDTO> findAll() {
        return this.procesoRepository.findAll()
                .stream()
                .map(mapper::procesoToProcesoDTO)
                .collect(Collectors.toList());
    }
}
