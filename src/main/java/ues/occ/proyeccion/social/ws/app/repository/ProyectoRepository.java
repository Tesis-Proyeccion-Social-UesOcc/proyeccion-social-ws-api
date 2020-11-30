package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;

@Repository
public interface ProyectoRepository extends CrudRepository<Proyecto, Integer>{

	@Query(value="SELECT p.id, p.nombre, p.duracion, p.interno, p.id_tutor, p.id_encargado_externo, p.fecha_creacion\r\n" + 
			"FROM chatbot_db.proyecto p\r\n" + 
			"INNER JOIN chatbot_db.proyecto_estudiante pe ON p.id = pe.id_proyecto\r\n" + 
			"WHERE pe.id_status = ?1  ORDER BY p.fecha_creacion DESC;", nativeQuery = true)
	public List<Proyecto> findProyectosByStatus(int idStatus);
}
