package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Personal;

@Repository
public interface PersonalRepository extends CrudRepository<Personal, Integer> {

	List<Personal> findByTipoPersonalId(int idTipoPersonal);

	List<Personal> findByDepartamentoId(int idDepartamento);

	List<Personal> findByNombreOrApellidoContaining(String nombre, String Apellido);

	/**
	 * @param areaCharSequence area/department name or null if the head is needed
	 * */
	@Query("SELECT p FROM Personal p INNER JOIN p.departamento d INNER JOIN p.personalEncargado p2 WHERE p.head = ?1 OR lower(d.nombre) LIKE lower(concat('%', ?2,'%'))")
	Optional<Personal> getPersonalEncargadoByDepartmentName(boolean isHead, String areaCharSequence);

}