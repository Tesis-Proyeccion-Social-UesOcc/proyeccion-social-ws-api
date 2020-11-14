package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.model.ProyectoDTO;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private EstudianteService estudianteService;
    private ProyectoService proyectoService;

    public EstudianteController(EstudianteService estudianteService, ProyectoService proyectoService) {
        this.estudianteService = estudianteService;
        this.proyectoService = proyectoService;
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
}
