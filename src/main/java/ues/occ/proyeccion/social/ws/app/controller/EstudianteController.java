package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.*;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.service.EstadoRequerimientoEstudianteService;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;
import ues.occ.proyeccion.social.ws.app.utils.MapperUtility;

import java.util.*;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private final EstudianteService estudianteService;
    private final ProyectoService proyectoService;
    private final EstadoRequerimientoEstudianteService estadoRequerimientoEstudianteService;
    private final CertificadoService certificadoService;

    public EstudianteController(EstudianteService estudianteService, ProyectoService proyectoService,
                                EstadoRequerimientoEstudianteService estadoRequerimientoEstudianteService,
                                CertificadoService certificadoService) {

        this.estudianteService = estudianteService;
        this.proyectoService = proyectoService;
        this.estadoRequerimientoEstudianteService = estadoRequerimientoEstudianteService;
        this.certificadoService = certificadoService;
    }

    @GetMapping
    public EstudianteDTOPage findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "si") Optional<String> isComplete
    ) {
        Page<EstudianteDTO> result = isComplete.map(MapperUtility::isComplete)
                .map(bool -> this.estudianteService.findAllByServicio(page, size, bool))
                .orElse(Page.empty());
        return new EstudianteDTOPage(result);
    }


    @GetMapping("/{carnet}")
    public EstudianteDTO getOne(@PathVariable String carnet) {
        return this.estudianteService.findByCarnet(carnet);
    }

    @PostMapping("/{carnet}/proyectos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProyectoCreationDTO createProject(
            @RequestBody ProyectoCreationDTO proyectoCreationDTO,
            @PathVariable String carnet) {

        return this.proyectoService.save(carnet, proyectoCreationDTO);

    }

    // HERE DOWN
    @GetMapping("/{carnet}/proyectos")
    public ProyectoDTOPage projectsByStudentID(
            @PathVariable String carnet,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "2") int status){
        Page<ProyectoCreationDTO.ProyectoDTO> result = this.proyectoService.findProyectosByEstudiante(page, size, carnet, status);
        return new ProyectoDTOPage(result);
    }

    @PostMapping("/{carnet}/documentos/{requerimientoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoRequerimientoEstudianteDTO addDocument(@PathVariable String carnet, @PathVariable int requerimientoId) {
        return this.estadoRequerimientoEstudianteService.save(carnet, requerimientoId).orElseThrow(
                () -> new InternalErrorException("Something went wrong")
        );
    }

    @GetMapping("/{carnet}/documentos")
    public EstadoRequerimientoEstudianteDTOPage getStudentDocuments(@PathVariable String carnet,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "si") Optional<String> aprobado){

        Page<EstadoRequerimientoEstudianteDTO> result = aprobado.map(MapperUtility::isAprobado)
                .map(bool -> this.estadoRequerimientoEstudianteService.findAllByCarnet(page, size, carnet, bool))
                .orElse(Page.empty());

        return new EstadoRequerimientoEstudianteDTOPage(result);
    }

    @GetMapping("/{carnet}/certificados")
    public CertificadoDTOPage getCertificate(
            @PathVariable String carnet,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<CertificadoCreationDTO.CertificadoDTO> result = this.certificadoService.findAllByCarnet(page, size, carnet);
        return new CertificadoDTOPage(result);
    }

}
