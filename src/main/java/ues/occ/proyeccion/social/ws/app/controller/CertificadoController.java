package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.model.CertificadoDTOList;
import ues.occ.proyeccion.social.ws.app.service.CertificadoService;
import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {
    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @PostMapping("/certificados")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificadoCreationDTO.CertificadoDTO createCertificate(@RequestBody CertificadoCreationDTO dto){
        return this.certificadoService.save(dto).orElseThrow(
                () -> new InternalErrorException("Certificate creation failed")
        );
    }

    @GetMapping("/certificados")
    public CertificadoDTOList createCertificate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        List<CertificadoCreationDTO.CertificadoDTO> result = this.certificadoService.findAll(page, size);
        return new CertificadoDTOList(result);
    }
}
