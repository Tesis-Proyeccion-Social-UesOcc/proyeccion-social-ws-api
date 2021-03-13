package ues.occ.proyeccion.social.ws.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;

@Repository
public interface CertificadoRepository extends PagingAndSortingRepository<Certificado, Integer> {
	Page<Certificado> findAllByProyecto_ProyectoEstudianteSet_Estudiante_Carnet(String carnet, Pageable pageable);

    @Query(value = "SELECT p.carnet FROM proyecto_estudiante p where p.id = ?1", nativeQuery = true)
    String findCarnet(int id);

    Optional<Certificado> findByProyecto_ProyectoEstudianteSet_Estudiante_CarnetIgnoreCaseAndProyecto_NombreIgnoreCase(String carnet, String projectName);

}

