package ues.occ.proyeccion.social.ws.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Acl;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.service.CertificadoServiceImpl;
import ues.occ.proyeccion.social.ws.app.service.StorageService;

@RestController
@RequestMapping(value = "/proyectos-estudiante")
public class ProyectoEstudianteController implements StorageService {
	
	//private final StorageService storageService;
	
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
		//storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		File fileToRead = new File(localFilePath.getFile(),fileName);
		byte [] data = Files.readAllBytes(Paths.get(fileToRead.toURI()));
		String url = storage.create(blobInfo, data).getSelfLink();
	
		Message message = new Message();
		message.setContents(new String(url));
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
	
	
	@PostMapping()
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		BlobId blobId = BlobId.of("certificados-documentos", "IR13002-"+file.getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		String url;
		try {
			
			 url = storage.create(blobInfo, file.getBytes()).getMediaLink();
			 storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			url="Error in the storage process";
		}
		
		return url;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}
	
/*	public ResponseEntity<Object> createJobProfile(
		@RequestPart(value = "files", required = true) MultipartFile file[]{
	}*/
}