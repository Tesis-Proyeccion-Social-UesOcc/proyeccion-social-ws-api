package ues.occ.proyeccion.social.ws.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.repository.PersonalRepository;

@Service
public class PersonalImpl implements PersonalService {

	private static final Logger log = LoggerFactory.getLogger(PersonalImpl.class);
	
	@Autowired
	private PersonalRepository personalRepository;
	
	@Override
	public ResponseEntity<ServiceResponse> findByTipoPersonal(int idTipoPersonal) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
					personalRepository.findByTipoPersonal(idTipoPersonal)), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Problemas al buscar por tipo personal: "+e.getMessage());
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal,
					"Problemas al buscar por tipo personal: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}