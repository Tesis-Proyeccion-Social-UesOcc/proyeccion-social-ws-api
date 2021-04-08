package ues.occ.proyeccion.social.ws.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.EstadoRequerimientoEstudianteMapper;
import ues.occ.proyeccion.social.ws.app.mappers.EstudianteMapper;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.repository.EstadoRequerimientoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.repository.EstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl extends PageableResource<Estudiante, EstudianteDTO> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper mapper;
    private final EstadoRequerimientoEstudianteMapper requerimientoEstudianteMapper;
    private final EstadoRequerimientoEstudianteRepository estadoRequerimientoEstudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, EstudianteMapper mapper, EstadoRequerimientoEstudianteMapper requerimientoEstudianteMapper,
                                 EstadoRequerimientoEstudianteRepository estadoRequerimientoEstudianteRepository) {
        this.estudianteRepository = estudianteRepository;
        this.mapper = mapper;
        this.requerimientoEstudianteMapper = requerimientoEstudianteMapper;
        this.estadoRequerimientoEstudianteRepository = estadoRequerimientoEstudianteRepository;
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

		var estadoRequerimientos = estadoRequerimientoEstudianteRepository.findByCarnet(carnet);
		List<EstadoRequerimientoEstudianteDTO.RequerimientoEstudianteDTO> result =
				estadoRequerimientos.stream().map(requerimientoEstudianteMapper::requerimientoEstudianteToDTO)
                        .collect(Collectors.toList());
		
		return new ResponseEntity<>(new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
				result)
				, HttpStatus.OK);
	}
}
