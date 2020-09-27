package ues.occ.proyeccion.social.ws.app.service;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoRepository;

@Service
public class ProyectoServiceImpl implements ProyectoService {

	private final ProyectoRepository proyectoRepository;

	public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
		this.proyectoRepository = proyectoRepository;
	}

	@Override
	public Proyecto findById(int id) {
		return this.proyectoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public ResponseEntity<ServiceResponse> findAll() {
		return new ResponseEntity<ServiceResponse>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, proyectoRepository.findAll()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ServiceResponse> create(Proyecto request) {
		Proyecto result = new Proyecto();
		try {
			
			request.setFechaCreacion(new Date());
			result = proyectoRepository.save(request);
			
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(
					ServiceResponse.codeOkCreated, ServiceResponse.messageCreated, result), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(
					ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		//return null;
	}
}
