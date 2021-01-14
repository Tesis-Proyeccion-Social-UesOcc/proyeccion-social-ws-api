package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.model.PageDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {
    private ProyectoService service;

    public ProyectoController(ProyectoService service) {
        this.service = service;
    }

    @GetMapping("/{projectId}")
    public Proyecto getOne(@PathVariable int projectId) {
        return this.service.findById(projectId);
    }

    @GetMapping
    public PageDTO<ProyectoCreationDTO.ProyectoDTO> getRange(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "status", required = false) Integer status
    ) {
        PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> result;
        if (status == null) {
            result = this.service.findAll(page, size);
        } else {
            result = this.service.findAllByStatus(page, size, status);
        }
        return new PageDTO<>(result);
    }

    @GetMapping("/pending")
    public PageDTO<ProyectoCreationDTO.ProyectoDTO> getPendingProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        var result =  this.service.findAllPending(page, size);
        return new PageDTO<>(result);
    }
}
