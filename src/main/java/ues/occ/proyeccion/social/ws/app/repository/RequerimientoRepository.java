package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.repository.projections.RequerimientoIdView;

import java.util.List;

@Repository
public interface RequerimientoRepository extends CrudRepository<Requerimiento, Integer>{
    List<RequerimientoIdView> findAllProjectedBy();
    @NonNull
    List<Requerimiento> findAll();
}
