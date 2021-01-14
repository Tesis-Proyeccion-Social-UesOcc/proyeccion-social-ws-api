package ues.occ.proyeccion.social.ws.app.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.util.UriComponentsBuilder;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.events.PaginatedResultsRetrievedEvent;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTOList;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTOPage;
import ues.occ.proyeccion.social.ws.app.model.PageDTO;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import java.util.List;

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
}
