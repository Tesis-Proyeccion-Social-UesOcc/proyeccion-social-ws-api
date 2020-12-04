package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;

@Repository
public interface CertificadoRepository extends PagingAndSortingRepository<Certificado, Integer> {
	Page<Certificado> findAllByProyectoEstudiante_Estudiante_Carnet(String carnet, Pageable pageable);
}
