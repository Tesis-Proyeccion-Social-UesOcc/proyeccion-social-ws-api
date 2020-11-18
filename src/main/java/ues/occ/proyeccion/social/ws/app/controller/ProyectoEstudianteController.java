package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.google.cloud.ReadChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.CertificadoServiceImpl;

@RestController
@RequestMapping(value = "/proyectos-estudiante")
public class ProyectoEstudianteController {
	
	@Autowired
	private Storage storage;
	@Autowired
	private CertificadoServiceImpl certificadoServiceImpl;
	
	@Value("${component.projectId.value}")
	private String projectId;

	@Value("${component.bucketName.value}")
	private String bucketName;

	
	@Value("${file.storage}")
	private Resource localFilePath;

	@PostMapping(value = "/certificado")
	public ResponseEntity<ServiceResponse> crearCertificado(@RequestBody Certificado certificado) {
		return certificadoServiceImpl.crearCertificado(certificado);
	}

	
	@RequestMapping(path = { "/{fileName}" }, method = { RequestMethod.GET })
	public Message writeFileToBucket(@PathVariable(name = "fileName") String fileName) throws IOException {
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();;
		BlobId blobId = BlobId.of(bucketName, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		File fileToRead = new File(localFilePath.getFile(),fileName);
		byte [] data = Files.readAllBytes(Paths.get(fileToRead.toURI()));
		storage.create(blobInfo, data);
		
		Message message = new Message();
		message.setContents(new String(data));
		return message;
	}

	@RequestMapping(path = { "/hello" }, method = { RequestMethod.GET })
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

class Message {
	private String contents;

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
