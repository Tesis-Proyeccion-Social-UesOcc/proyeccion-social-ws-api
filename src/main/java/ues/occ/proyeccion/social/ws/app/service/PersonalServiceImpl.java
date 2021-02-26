package ues.occ.proyeccion.social.ws.app.service;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.dto.PersonalExternoDto;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.PersonalEncargadoMapper;
import ues.occ.proyeccion.social.ws.app.model.PersonalEncargadoDTO;
import ues.occ.proyeccion.social.ws.app.repository.PersonaExternoRepository;
import ues.occ.proyeccion.social.ws.app.repository.PersonalRepository;

@Service
public class PersonalServiceImpl implements PersonalService {

	private static final Logger log = LoggerFactory.getLogger(PersonalServiceImpl.class);
	
	@Autowired
	private PersonalRepository personalRepository;
	@Autowired
	private PersonaExternoRepository personaExternoRepository;

	private final PersonalEncargadoMapper mapper;
	
	public PersonalServiceImpl(PersonalRepository personalRepository, PersonalEncargadoMapper mapper) {
		this.personalRepository = personalRepository;
		this.mapper = mapper;
	}
	
	private ModelMapper modelMapper;
	
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
	public ResponseEntity<ServiceResponse> findAll(int interno) {
		modelMapper = new ModelMapper();
		if(interno == 1) {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
					 personalRepository.findAll()), HttpStatus.OK);
		} else {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
					 personaExternoRepository.findAll().stream().
					 map(element -> modelMapper.map(element, PersonalExternoDto.class)).collect(Collectors.toList())
			), HttpStatus.OK);
			
		}
	
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
	public ResponseEntity<ServiceResponse> findAll() {
		return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
				 personalRepository.findAll()), HttpStatus.OK);
	}
	
	@Override
	public PersonalEncargadoDTO findByDepartmentName(String departmentName) {
		var personalOptional = this.personalRepository.getPersonalEncargadoByDepartmentName(departmentName);
		return personalOptional.map(mapper::personalToEncangadoDTO)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("There's no personal for department %s", departmentName)));
	}
	
}
