package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.ProcesoService;

@RestController
@RequestMapping("/procesos")
public class ProcesoController {

    private final ProcesoService procesoService;

    public ProcesoController(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> getAll(){
        var data = this.procesoService.findAll();
        return new ResponseEntity<>(
                new ServiceResponse(ServiceResponse.CODE_OK, ServiceResponse.MESSAGE_OK, data),
                HttpStatus.OK);
    }
}
