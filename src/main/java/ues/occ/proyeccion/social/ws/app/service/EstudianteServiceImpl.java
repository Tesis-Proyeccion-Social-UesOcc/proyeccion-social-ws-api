package ues.occ.proyeccion.social.ws.app.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.EstadoRequerimientoEstudiante;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.dto.EstadoRequerimientoEstudianteDto;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.EstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

@Service
public class EstudianteServiceImpl extends PageableResource<Estudiante, EstudianteDTO> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper mapper;
    
    @Autowired
    private  EstadoRequerimientoEstudianteRepository estadoRequerimientoEstudianteRepository;
    
	private ModelMapper modelMapper;
	
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, EstudianteMapper mapper) {
        this.estudianteRepository = estudianteRepository;
        this.mapper = mapper;
    }

    @Override
    public PageDtoWrapper<Estudiante, EstudianteDTO> findAllByServicio(int page, int size, boolean isComplete) {
        Pageable pageable = this.getPageable(page, size);
        Page<Estudiante> estudiantePage = this.estudianteRepository.findAllByServicioCompleto(pageable, isComplete);
        return this.getPagedData(estudiantePage);
    }

    @Override
    public EstudianteDTO findByCarnet(String carnet) {
        carnet = carnet.strip();
        if (carnet.length() != 7) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        Optional<Estudiante> estudiante = this.estudianteRepository.findByCarnetIgnoreCase(carnet);
        return estudiante.map(this.mapper::estudianteToEstudianteDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student with the given ID does not exists"));
    }

    @Override
    protected Function<Estudiante, EstudianteDTO> getMapperFunction() {
        return this.mapper::estudianteToEstudianteDTO;
    }

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<ServiceResponse> getRequirementStatusByCardId(String carnet) {
		 modelMapper = new ModelMapper();
		List<EstadoRequerimientoEstudiante> estadoRequerimientos = estadoRequerimientoEstudianteRepository.findByCarnet(carnet);
		List<EstadoRequerimientoEstudianteDto> result = 
				estadoRequerimientos.stream().map(element -> modelMapper.map(element, EstadoRequerimientoEstudianteDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
				result)
				, HttpStatus.OK);
	}
}
