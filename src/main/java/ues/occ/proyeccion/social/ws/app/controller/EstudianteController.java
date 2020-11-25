package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.EstadoRequerimientoEstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoDTO;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.service.EstadoRequerimientoEstudianteService;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;

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
        List list = isComplete.map(this::isComplete)
                .map(bool -> this.estudianteService.findAllByServicio(page, size, bool))
                .orElse(Collections.emptyList());
        return list;
    }

    private boolean isComplete(String isCompleteString) {
        if ("si".equals(isCompleteString)) {
            return true;
        } else if ("no".equals(isCompleteString)) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid parameters, valid are: si or no");
        }
    }

    @GetMapping("/{carnet}")
    public Estudiante getOne(@PathVariable String carnet) {
        return this.estudianteService.findByCarnet(carnet);
    }

    @PostMapping(value = "/{carnet}/proyectos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProyectoDTO proyectosByEstudiante(
            @RequestBody ProyectoDTO proyectoDTO,
            @PathVariable String carnet) {

        return this.proyectoService.save(
                this.estudianteService.findByCarnet(carnet),
                proyectoDTO
        );

    }

    @PostMapping("/{carnet}/documentos/{requerimientoId}")
    public EstadoRequerimientoEstudianteDTO addDocument(@PathVariable String carnet, @PathVariable int requerimientoId) {
        return this.estadoRequerimientoEstudianteService.save(carnet, requerimientoId).orElseThrow(
                () -> new InternalErrorException("Something went wrong")
        );
    }

    // idProyectoEstudiante is needed due to OneToOne relationship
    @PostMapping("/certificate/{idProyectoEstudiante}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createCertificate(@PathVariable int idProyectoEstudiante, @RequestBody HashMap<String, String> urlInfo){
        String urlStr = urlInfo.get("url");
        Certificado result = this.certificadoService.save(urlStr, idProyectoEstudiante).orElseThrow(
                () -> new InternalErrorException("Certificate creation failed")
        );
        return Map.of("url", result.getUri());
    }

}
