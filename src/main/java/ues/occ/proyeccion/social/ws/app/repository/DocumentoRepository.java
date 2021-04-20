package ues.occ.proyeccion.social.ws.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ues.occ.proyeccion.social.ws.app.dao.Documento;

import java.util.List;


@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    @Query("select d from Documento d inner join d.requerimientos r inner join r.estadoRequerimientoEstudiantes ere " +
            "inner join ere.proyectoEstudiante pe inner join pe.estudiante e inner join pe.proyecto p " +
            "where upper(p.nombre) = upper(?2) and upper(e.carnet) = upper(?1) ")
    List<Documento> findProjectRelatedDocuments(String carnet, String projectname);


}
