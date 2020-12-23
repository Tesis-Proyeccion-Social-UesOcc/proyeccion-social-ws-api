package ues.occ.proyeccion.social.ws.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.RequerimientoRepository;

@SpringBootApplication
public class ProyeccionSocialWsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyeccionSocialWsApiApplication.class, args);
	}

}

@Component
class Bootstrap implements ApplicationListener<ContextRefreshedEvent>{

	private EstudianteRepository estudianteRepository;
	private RequerimientoRepository requerimientoRepository;

	public Bootstrap(EstudianteRepository estudianteRepository, RequerimientoRepository requerimientoRepository) {
		this.estudianteRepository = estudianteRepository;
		this.requerimientoRepository = requerimientoRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		Estudiante estudiante = new Estudiante();
		estudiante.setCarnet("zh15002");
		Requerimiento requerimiento = new Requerimiento();
		requerimiento.setId(1);
		var estudianteResult = estudianteRepository.save(estudiante);
		var requerimientoResult = requerimientoRepository.save(requerimiento);
	}
}