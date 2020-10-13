package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.Estudiante;
import ues.occ.proyeccion.social.ws.app.service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping(params = {"page", "size"})
    public List<Estudiante> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List list = this.estudianteService.findAll(page, size);
        return list;
    }

    @GetMapping("/{carnet}")
    public Estudiante getOne(@PathVariable String carnet){
        return this.estudianteService.findByCarnet(carnet);
    }
}
