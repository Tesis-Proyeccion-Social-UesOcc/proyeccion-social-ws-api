package ues.occ.proyeccion.social.ws.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ues.occ.proyeccion.social.ws.app.dao.Documento;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.repository.DocumentoRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.RequerimientoRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class ProyeccionSocialWsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyeccionSocialWsApiApplication.class, args);
	}

}

@Component
@Profile("test")
class Bootstrap implements ApplicationListener<ContextRefreshedEvent>{

	private final EstudianteRepository estudianteRepository;
	private final RequerimientoRepository requerimientoRepository;
	private final DocumentoRepository documentoRepository;

	public Bootstrap(EstudianteRepository estudianteRepository, RequerimientoRepository requerimientoRepository, DocumentoRepository documentoRepository) {
		this.estudianteRepository = estudianteRepository;
		this.requerimientoRepository = requerimientoRepository;
		this.documentoRepository = documentoRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		Estudiante estudiante = new Estudiante();
		estudiante.setCarnet("zh15002");
		Requerimiento requerimiento = new Requerimiento();
		Documento documento = new Documento("Nombre", "Descripcion", "https://google.com", LocalDateTime.now());
		var docResult = documentoRepository.save(documento);
		requerimiento.setId(1);
		requerimiento.setDocumento(docResult);
		var estudianteResult = estudianteRepository.save(estudiante);
		var requerimientoResult = requerimientoRepository.save(requerimiento);
	}
}