package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.events.PaginatedResultsRetrievedEvent;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.*;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.service.EstadoRequerimientoEstudianteService;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;
import ues.occ.proyeccion.social.ws.app.utils.MapperUtility;
import ues.occ.proyeccion.social.ws.app.utils.StatusOption;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private final EstudianteService estudianteService;
    private final ProyectoService proyectoService;
    private final EstadoRequerimientoEstudianteService estadoRequerimientoEstudianteService;
    private final CertificadoService certificadoService;
    private final ApplicationEventPublisher publisher;

    public EstudianteController(EstudianteService estudianteService, ProyectoService proyectoService,
                                EstadoRequerimientoEstudianteService estadoRequerimientoEstudianteService,
                                CertificadoService certificadoService, ApplicationEventPublisher publisher) {

        this.estudianteService = estudianteService;
        this.proyectoService = proyectoService;
        this.estadoRequerimientoEstudianteService = estadoRequerimientoEstudianteService;
        this.certificadoService = certificadoService;
        this.publisher = publisher;
    }

    @GetMapping
    public PageDTO<EstudianteDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "si") Optional<String> isComplete,
            UriComponentsBuilder builder, HttpServletResponse response
    ) {
        int totalPages = 0;
        PageDTO<EstudianteDTO> toReturn;
        var result = isComplete.map(MapperUtility::isComplete)
                .map(bool -> this.estudianteService.findAllByServicio(page, size, bool))
                .orElse(null);

        if (result == null) {
            toReturn = new PageDTO<>();
        } else {
            totalPages = result.getOriginalPage().getTotalPages();
            toReturn = new PageDTO<>(result);
        }
        publisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                this.getClass(), builder, response, page,
                totalPages, size)
        );
        return toReturn;
    }


    @GetMapping("/{carnet}")
    public EstudianteDTO getOne(@PathVariable String carnet) {
        return this.estudianteService.findByCarnet(carnet);
    }

    @PostMapping("/{carnet}/proyectos")
    @ResponseStatus(HttpStatus.CREATED)
    public String createProject(
            @RequestBody ProyectoCreationDTO proyectoCreationDTO,
            @PathVariable String carnet) {

        return "deprecated";

    }

    // HERE DOWN
    @GetMapping("/{carnet}/proyectos")
    public ResponseEntity<PageDTO<? extends ProjectMarker>> projectsByStudentID(
            @PathVariable String carnet,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "2") int status,
            UriComponentsBuilder builder, HttpServletResponse response) {

        var result = status == StatusOption.PENDIENTE ?
                this.proyectoService.findProyectosPendientesByEstudiante(page, size, carnet, status) :
                this.proyectoService.findProyectosByEstudiante(page, size, carnet, status);

        publisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                this.getClass(), builder, response, page,
                result.getOriginalPage().getTotalPages(), size)
        );
        return new ResponseEntity<>(new PageDTO<>(result), HttpStatus.OK);
    }

    @GetMapping("/{carnet}/proyectos/single")
    public ProjectMarker studentProjectByName(@PathVariable String carnet, @RequestParam String projectName){
        var decodedProjectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
        return this.proyectoService.findByCarnetAndProjectName(carnet, decodedProjectName);
    }

    @GetMapping("/{carnet}/proyectos/estadoRequerimiento")
    public ResponseEntity<ServiceResponse> getRequirementStatus(@PathVariable String carnet){
        var data = this.proyectoService.getRequirementsData(carnet);
        return new ResponseEntity<>(
                new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, data),
                HttpStatus.OK);
    }

    @PostMapping("/{idProyectoEstudiante}/documentos/{requerimientoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoRequerimientoEstudianteDTO addDocument(@PathVariable Integer idProyectoEstudiante, @PathVariable int requerimientoId) {
        return this.estadoRequerimientoEstudianteService.save(idProyectoEstudiante, requerimientoId).orElseThrow(
                () -> new InternalErrorException("Something went wrong")
        );
    }

    @GetMapping("/{carnet}/documentos")
    public PageDTO<EstadoRequerimientoEstudianteDTO> getStudentDocuments(
            @PathVariable String carnet,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "si") Optional<String> aprobado,
            UriComponentsBuilder builder, HttpServletResponse response) {
        int totalPages = 0;
        PageDTO<EstadoRequerimientoEstudianteDTO> toReturn = null;
        var result = aprobado.map(MapperUtility::isAprobado)
                .map(bool -> this.estadoRequerimientoEstudianteService.findAllByCarnet(page, size, carnet, bool))
                .orElse(null);
        if (result == null) {
            toReturn = new PageDTO<>();
        } else {
            totalPages = result.getOriginalPage().getTotalPages();
            toReturn = new PageDTO<>(result);
        }
        publisher.publishEvent(new PaginatedResultsRetrievedEvent<>(this.getClass(), builder, response, page, totalPages, size));
        return toReturn;
    }

    @GetMapping("/{carnet}/certificados")
    public PageDTO<CertificadoCreationDTO.CertificadoDTO> getCertificate(
            @PathVariable String carnet,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            UriComponentsBuilder builder, HttpServletResponse response) {
        var result = this.certificadoService.findAllByCarnet(page, size, carnet);
        publisher.publishEvent(new PaginatedResultsRetrievedEvent<>(this.getClass(), builder, response, page,
                result.getOriginalPage().getTotalPages(), size));
        return new PageDTO<>(result);
    }
    
    @GetMapping("/{carnet}/estadoRequerimiento2")
    public ResponseEntity<ServiceResponse> getRequirementStatusByCardId(@PathVariable String carnet){
    	return estudianteService.getRequirementStatusByCardId(carnet);
    }

}
