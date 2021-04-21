package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.events.PaginatedResultsRetrievedEvent;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.PageDTO;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


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
    
    @PostMapping("/{proyectoEstudianteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificadoCreationDTO.CertificadoDTO createUploadCertificate(@PathVariable int proyectoEstudianteId, @RequestParam MultipartFile file){
        return this.certificadoService.uploadCertificate(proyectoEstudianteId, file).orElseThrow(
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


    @GetMapping("/{carnet}")
    public CertificadoCreationDTO.CertificadoDTO getCertificateByProjectName(@PathVariable String carnet,
                                                                             @RequestParam(name = "projectName") String projectName){
        var decodedProjectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);

        return this.certificadoService.getCertificate(carnet, decodedProjectName);
    }

}
