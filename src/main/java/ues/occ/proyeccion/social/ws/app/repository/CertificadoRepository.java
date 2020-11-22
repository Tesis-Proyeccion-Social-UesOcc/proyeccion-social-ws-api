package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;

@Repository
public interface CertificadoRepository extends CrudRepository<Certificado, Integer> {
	
	@Query(value = "SELECT p.carnet FROM proyecto_estudiante p where p.id = ?1", nativeQuery = true)
	String findCarnet(int id);
}
