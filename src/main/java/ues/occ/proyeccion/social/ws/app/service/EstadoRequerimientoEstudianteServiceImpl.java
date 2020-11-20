package ues.occ.proyeccion.social.ws.app.service;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class EstadoRequerimientoEstudianteServiceImpl implements EstadoRequerimientoEstudianteService {

    @PersistenceContext
    private EntityManager entityManager;

    private EstadoRequerimientoEstudianteRepository repository;

    public EstadoRequerimientoEstudianteServiceImpl(EstadoRequerimientoEstudianteRepository repository) {
        this.repository = repository;
    }

    // todo: refactor repositories to add entityManager and getSession method, that's needed to use only the entities ID in insertions, otherwise findById would be used
    private Session getSession(){
        return this.entityManager.unwrap(Session.class);
    }

    @Override
    public boolean save(String carnet, int requerimientoId) {
        try {
            Estudiante estudiante = this.getSession().getReference(Estudiante.class, carnet);
            Requerimiento requerimiento = this.getSession().getReference(Requerimiento.class, requerimientoId);
            EstadoRequerimientoEstudiante estadoRequerimientoEstudiante = new EstadoRequerimientoEstudiante(estudiante, requerimiento, true, true, null, null);
            repository.save(estadoRequerimientoEstudiante);
            return true
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
