package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import ues.occ.proyeccion.social.ws.app.dao.Documento;
import ues.occ.proyeccion.social.ws.app.dao.Plantilla;

import java.util.List;

public interface PlantillaRepository extends CrudRepository<Plantilla, Integer> {

    List<Plantilla> findByNombreContainingIgnoreCaseOrderByFechaDocumento(String nombre);

}
