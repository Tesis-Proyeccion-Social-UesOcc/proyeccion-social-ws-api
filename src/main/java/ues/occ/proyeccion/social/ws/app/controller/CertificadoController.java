package ues.occ.proyeccion.social.ws.app.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTOPage;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {
    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificadoCreationDTO.CertificadoDTO createCertificate(@Valid @RequestBody CertificadoCreationDTO dto){
        return this.certificadoService.save(dto).orElseThrow(
                () -> new InternalErrorException("Certificate creation failed")
        );
    }

    @GetMapping
    public CertificadoDTOPage getCertificates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<CertificadoCreationDTO.CertificadoDTO> result = this.certificadoService.findAll(page, size);
        return new CertificadoDTOPage(result);
    }
}
