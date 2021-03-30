package ues.occ.proyeccion.social.ws.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ues.occ.proyeccion.social.ws.app.dao.Documento;


@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {


}
