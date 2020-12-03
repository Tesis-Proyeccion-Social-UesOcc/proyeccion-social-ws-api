package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
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
    public List<Estudiante> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "si") Optional<String> isComplete
    ) {
        // TODO: refactor with DTO
        List list = isComplete.map(MapperUtility::isComplete)
                .map(bool -> this.estudianteService.findAllByServicio(page, size, bool))
                .orElse(Collections.emptyList());
        return list;
    }



    @GetMapping("/{carnet}")
    public Estudiante getOne(@PathVariable String carnet) {
        return this.estudianteService.findByCarnet(carnet);
    }

    @PostMapping("/{carnet}/proyectos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProyectoCreationDTO createProject(
            @RequestBody ProyectoCreationDTO proyectoCreationDTO,
            @PathVariable String carnet) {

        return this.proyectoService.save(
                this.estudianteService.findByCarnet(carnet),
                proyectoCreationDTO
        );

    }

    // TODO: Add implementation on proyectoService
    @GetMapping("/{carnet}/proyectos")
    public ProyectoDTOList projectsByStudentID(
            @PathVariable String carnet,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "2") int status){
        List<ProyectoCreationDTO.ProyectoDTO> result = this.proyectoService.findProyectosByEstudiante(page, size, carnet, status);
        return new ProyectoDTOList(result);
    }

    @PostMapping("/{carnet}/documentos/{requerimientoId}")
    public EstadoRequerimientoEstudianteDTO addDocument(@PathVariable String carnet, @PathVariable int requerimientoId) {
        return this.estadoRequerimientoEstudianteService.save(carnet, requerimientoId).orElseThrow(
                () -> new InternalErrorException("Something went wrong")
        );
    }

    @GetMapping("/carnet/documentos")
    public EstadoRequerimientoEstudianteDTOList getStudentDocuments(@PathVariable String carnet,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "si") Optional<String> aprobado){

        List<EstadoRequerimientoEstudianteDTO> result = aprobado.map(MapperUtility::isAprobado)
                .map(bool -> this.estadoRequerimientoEstudianteService.findAllByCarnet(page, size, carnet, bool))
                .orElse(Collections.emptyList());

        return new EstadoRequerimientoEstudianteDTOList(result);
    }

    // idProyectoEstudiante is needed due to OneToOne relationship
    @PostMapping("/certificados/{idProyectoEstudiante}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createCertificate(@PathVariable int idProyectoEstudiante, @RequestBody HashMap<String, String> urlInfo){
        String urlStr = urlInfo.get("url");
        Certificado result = this.certificadoService.save(urlStr, idProyectoEstudiante).orElseThrow(
                () -> new InternalErrorException("Certificate creation failed")
        );
        return Map.of("url", result.getUri());
    }

    @GetMapping("/certificados")
    public CertificadoDTOList createCertificate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        List<CertificadoDTO> result = this.certificadoService.findAll(page, size);
        return new CertificadoDTOList(result);
    }

}
