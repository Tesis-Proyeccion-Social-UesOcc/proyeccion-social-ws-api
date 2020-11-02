package ues.occ.proyeccion.social.ws.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {


	private String allowedOrigins = "*";

	private String allowedHeaders = "*";

	private String allowedMethods = "*";
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(allowedOrigins).allowedHeaders(allowedHeaders).allowedMethods(allowedMethods);
			}
	    };
	}
}
