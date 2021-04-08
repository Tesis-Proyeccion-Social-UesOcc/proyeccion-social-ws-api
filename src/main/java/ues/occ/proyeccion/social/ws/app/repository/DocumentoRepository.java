package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Documento;


@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    @Query(value =
            "SELECT * from documento d inner join requerimiento r on r.id_documento = d.id inner join estado_requerimiento_estudiante ere"
                    + " on ere.id_requerimiento = r.id RIGHT join proyecto_estudiante pe on pe.id_proyecto_estudiante = ere.id_proyecto_estudiante" +
                    " inner join proyecto p on p.id = pe.id_proyecto where upper(p.nombre) = upper(:projectName) and upper(pe.carnet) = upper(:carnet)",
            nativeQuery = true)
    List<Documento> findProjectRelatedDocuments(@Param("carnet") String carnet, @Param("projectName") String projectname);


}
