package ues.occ.proyeccion.social.ws.app.controller;

import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Storage;

import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.CertificadoServiceImpl;

@RestController
@RequestMapping(value = "/proyectos-estudiante")
public class ProyectoEstudianteController {

    // private final StorageService storageService;

    @Autowired
    private Storage storage;
    @Autowired
    private CertificadoServiceImpl certificadoServiceImpl;

    @PostMapping(value = "/certificado/{idProyectoEstudiante}")
    public ResponseEntity<ServiceResponse> crearCertificado(
            @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
            @PathVariable("idProyectoEstudiante") int id) {
        return certificadoServiceImpl.crearCertificado(id, file, redirectAttributes);
    }

    @RequestMapping(path = {"/hello"}, method = {RequestMethod.GET})
    public Message readFromFile() throws Exception {

        StringBuilder sb = new StringBuilder();

        try (ReadChannel channel = storage.reader("carbonrider", "uri-chatbot.pdf")) {
            ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
            while (channel.read(bytes) > 0) {
                bytes.flip();
                String data = new String(bytes.array(), 0, bytes.limit());
                sb.append(data);
                bytes.clear();
            }
        }

        Message message = new Message();
        message.setContents(sb.toString());

        return message;
    }
}