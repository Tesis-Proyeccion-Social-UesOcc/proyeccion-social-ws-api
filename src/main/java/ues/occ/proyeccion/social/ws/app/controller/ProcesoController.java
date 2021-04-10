package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.occ.proyeccion.social.ws.app.model.ProcesoDTO;
import ues.occ.proyeccion.social.ws.app.service.ProcesoService;

import java.util.List;

@RestController
@RequestMapping("/procesos")
public class ProcesoController {

    private final ProcesoService procesoService;

    public ProcesoController(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    @GetMapping
    public List<ProcesoDTO> getAll(){
        return this.procesoService.findAll();
    }
}
