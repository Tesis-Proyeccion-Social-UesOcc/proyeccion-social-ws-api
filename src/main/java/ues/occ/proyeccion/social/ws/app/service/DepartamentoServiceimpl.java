package ues.occ.proyeccion.social.ws.app.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.Departamento;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.repository.DepartamentoRepository;

@Service
public class DepartamentoServiceimpl implements DepartamentoService{

	public static Logger log = LoggerFactory.getLogger(DepartamentoServiceimpl.class);
	
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@Override
	public ResponseEntity<ServiceResponse> findAll() {

		try {
			
		return new ResponseEntity<ServiceResponse>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, departamentoRepository.findAll()), 
				HttpStatus.OK);	
		
		} catch (Exception e) {
			e.getCause();
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<ServiceResponse> findById(int id) {
		log.info("findById");
		try {
			Departamento departamento = departamentoRepository.findById(id).get();
			if(departamento != null) {
				log.info("{}", departamento.toString());
				return new ResponseEntity<ServiceResponse>(
						new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, departamento), HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse>(
						new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageNull, departamento), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Time: "+new Date(), e.getMessage());
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ServiceResponse> create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ServiceResponse> deleteById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ServiceResponse> updateById() {
		// TODO Auto-generated method stub
		return null;
	}

}
