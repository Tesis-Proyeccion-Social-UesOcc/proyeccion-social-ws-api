package ues.occ.proyeccion.social.ws.app.service;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public Proyecto findById(int id) {
       return this.proyectoRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
