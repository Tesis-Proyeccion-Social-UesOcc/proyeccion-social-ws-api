package ues.occ.proyeccion.social.ws.app.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.events.PaginatedResultsRetrievedEvent;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.PageDTO;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.validators.CarnetValidator;


@RestController
@RequestMapping("/certificados")
public class CertificadoController {
    private final CertificadoService certificadoService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public CertificadoController(CertificadoService certificadoService, ApplicationEventPublisher eventPublisher) {
        this.certificadoService = certificadoService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificadoCreationDTO.CertificadoDTO createCertificate(@Valid @RequestBody CertificadoCreationDTO dto){
        return this.certificadoService.save(dto).orElseThrow(
                () -> new InternalErrorException("Certificate creation failed")
        );
    }

    @GetMapping
    public PageDTO<CertificadoCreationDTO.CertificadoDTO> getCertificates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            UriComponentsBuilder builder,
            HttpServletResponse response){

        var result = this.certificadoService.findAll(page, size);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                Certificado.class, builder, response, page,
                result.getOriginalPage().getTotalPages(), size)
        );

        return new PageDTO<CertificadoCreationDTO.CertificadoDTO>(result);
    }


    @GetMapping("/{carnet}/")
    public CertificadoCreationDTO.CertificadoDTO getCertificateByProjectName(@PathVariable @CarnetValidator String carnet,
                                                                             @RequestParam(name = "projectName") @NotBlank String projectName){
        return this.certificadoService.getCertificate(carnet, projectName);
    }

}
