package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;
import ues.occ.proyeccion.social.ws.app.events.PaginatedResultsRetrievedEvent;
import ues.occ.proyeccion.social.ws.app.model.PageDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO;
import ues.occ.proyeccion.social.ws.app.service.ProyectoService;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService service;
    private final ApplicationEventPublisher publisher;

    public ProyectoController(ProyectoService service, ApplicationEventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    @GetMapping("/{projectId}")
    public ProyectoCreationDTO.ProyectoDTO getOne(@PathVariable int projectId) {
        return this.service.findById(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProyectoCreationDTO.ProyectoDTO create(@Valid @RequestBody ProyectoCreationDTO proyectoCreationDTO){
        return this.service.save(proyectoCreationDTO);
    }

    @PutMapping("/{projectId}")
    public ProyectoCreationDTO.ProyectoDTO update(@Valid @RequestBody ProyectoCreationDTO proyectoCreationDTO, @PathVariable int projectId){
        return this.service.update(proyectoCreationDTO, projectId);
    }

    @GetMapping
    public PageDTO<ProyectoCreationDTO.ProyectoDTO> getRange(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "status", required = false) Integer status,
            UriComponentsBuilder builder, HttpServletResponse response
    ) {
        PageDtoWrapper<Proyecto, ProyectoCreationDTO.ProyectoDTO> result;
        if (status == null) {
            result = this.service.findAll(page, size);
        } else {
            result = this.service.findAllByStatus(page, size, status);
        }
        this.publish(builder, response, size, page, result.getOriginalPage().getTotalPages());
        return new PageDTO<>(result);
    }

    @GetMapping("/pending")
    public PageDTO<ProyectoCreationDTO.ProyectoDTO> getPendingProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            UriComponentsBuilder builder, HttpServletResponse response
            ) {
        var result =  this.service.findAllPending(page, size);
        this.publish(builder, response, size, page, result.getOriginalPage().getTotalPages());
        return new PageDTO<>(result);
    }

    private void publish(UriComponentsBuilder builder, HttpServletResponse response, int size, int page, int totalPages){
        this.publisher.publishEvent(new PaginatedResultsRetrievedEvent<>(this.getClass(), builder, response, page, totalPages, size));
    }
}
