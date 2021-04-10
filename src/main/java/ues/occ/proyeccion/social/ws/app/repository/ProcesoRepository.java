package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Proceso;

import javax.annotation.Nonnull;
import java.util.List;

@Repository
public interface ProcesoRepository extends CrudRepository<Proceso, Integer>{
    @Nonnull
    List<Proceso> findAll();
}
