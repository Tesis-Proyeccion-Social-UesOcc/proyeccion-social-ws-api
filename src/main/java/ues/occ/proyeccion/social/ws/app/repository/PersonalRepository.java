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
	 * @param areaCharSequence area/department name
	 * */
	@Query("SELECT p from Personal p INNER JOIN p.departamento d INNER JOIN p.personalEncargado pe WHERE lower(d.nombre) like lower(concat('%', ?1,'%'))")
	Optional<Personal> getPersonalByDepartmentName(String areaCharSequence);

	Optional<Personal> findByTipoPersonal_Id(int typeId);
}