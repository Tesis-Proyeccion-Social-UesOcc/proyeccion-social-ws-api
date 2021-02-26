package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Personal;

@Repository
public interface PersonalRepository extends CrudRepository<Personal, Integer> {

	List<Personal> findByTipoPersonalId(int idTipoPersonal);

	List<Personal> findByDepartamentoId(int idDepartamento);

	List<Personal> findByNombreOrApellidoContaining(String nombre, String Apellido);

	Optional<Personal> findByDepartamento_NombreContainingIgnoreCase(String departamentoCharSequence);

}