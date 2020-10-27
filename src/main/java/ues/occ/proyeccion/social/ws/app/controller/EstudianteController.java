package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
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
}
