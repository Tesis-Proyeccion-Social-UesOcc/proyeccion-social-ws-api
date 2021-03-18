package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Documento;


@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

	List<Documento> findByNombreContainingIgnoreCaseOrderByFechaDocumento(String nombre);
	List<Documento> findAllByRequerimientos_EstadoRequerimientoEstudiantes_Estudiante_CarnetIgnoreCase(String carnet);

	@Query("select d from Documento d inner join d.requerimientos re inner join re.estadoRequerimientoEstudiantes ere " +
			"inner join ere.estudiante e inner join e.proyectoEstudianteSet pes inner join pes.proyecto p where p.status.id = ?1 " +
			"and upper(e.carnet) = upper(?2) and upper(p.nombre) = upper(?3)")
	List<Documento> findProjectRelatedDocuments(int status, String carnet, String projectname);
}
