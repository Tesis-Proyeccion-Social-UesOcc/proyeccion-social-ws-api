package ues.occ.proyeccion.social.ws.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.PersonalEncargadoMapper;
import ues.occ.proyeccion.social.ws.app.model.PersonalEncargadoDTO;
import ues.occ.proyeccion.social.ws.app.repository.PersonalRepository;

@Service
public class PersonalServiceImpl implements PersonalService {

	private static final Logger log = LoggerFactory.getLogger(PersonalServiceImpl.class);

	private final PersonalRepository personalRepository;
	private final PersonalEncargadoMapper mapper;


	public PersonalServiceImpl(PersonalRepository personalRepository, PersonalEncargadoMapper mapper) {
		this.personalRepository = personalRepository;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<ServiceResponse> findByTipoPersonalId(int idTipoPersonal) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
					 personalRepository.findByTipoPersonalId(idTipoPersonal)), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Problemas al buscar por tipo personal: "+e.getMessage());
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal,
					"Problemas al buscar por tipo personal: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<ServiceResponse> findAll() {
		return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
				 personalRepository.findAll()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ServiceResponse> findByDepartamentoId(int idDepartamento) {
		try {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
					 personalRepository.findByDepartamentoId(idDepartamento)), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Problemas al buscar por Departamento: "+e.getMessage());
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal,
					"Problemas al buscar por Departamento: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ServiceResponse> findByNombre(String nombre, String apellido) {
		try {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
					 personalRepository.findByNombreOrApellidoContaining(nombre, apellido)), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Problemas al buscar por nombre de personal: "+e.getMessage());
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal,
					"Problemas al buscar por nombre de personal: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public PersonalEncargadoDTO findByDepartmentName(String departmentName) {
		var personalOptional = this.personalRepository.findByPersonalEncargadoExistsAndDepartamento_NombreContainingIgnoreCase(departmentName);
		return personalOptional.map(mapper::personalToEncangadoDTO)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("There's no personal for department %s", departmentName)));
	}
}
