package ues.occ.proyeccion.social.ws.app.service;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;

@Service
public class CertificadoServiceImpl implements CertificadoService {

	private static final Logger log = LoggerFactory.getLogger(CertificadoServiceImpl.class);

	@Autowired
	private Storage storage;
	
	@Autowired
	private CertificadoRepository certificadoRepository;

	@Value("${component.bucketName.certificados.value}")
	private String bucketName;
	
	@Override
	public ResponseEntity<ServiceResponse> crearCertificado(int id,  MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			//validate.validator.validate(certificado);
			Certificado certificado =  new Certificado();
			store(file);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			String carnet =  certificadoRepository.findCarnet(id);
			if(carnet == null) {
				return new ResponseEntity<ServiceResponse>(
						new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, "Registro de Proyecto no encontrado, no se guardo el certificado"),
						HttpStatus.CREATED);
			}
			
			BlobId blobId = BlobId.of(bucketName, carnet.toUpperCase()+"-" + file.getOriginalFilename());
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			String url;
			try {

				url = storage.create(blobInfo, file.getBytes()).getMediaLink();
				storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
				log.info("uri "+url);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("No se logro guardar el documento en el bucket", e);
				return new ResponseEntity<ServiceResponse>(
						new ServiceResponse(ServiceResponse.codeFailStorageDocumentBucket, 
								ServiceResponse.messageFailStorageDocumentBucket, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(url != null && id > 0) {
				certificado.setId(id);
				certificado.setFechaExpedicion(LocalDateTime.now());
				log.info("Se creo un certificado " + certificado.toString());
				certificado.setUri(url);
				Certificado result = certificadoRepository.save(certificado);
				return new ResponseEntity<ServiceResponse>(
						new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, result),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ServiceResponse>(
						new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, "Registro no creado por falta de la creacion de la URI"),
						HttpStatus.CREATED);
				
			}
			
		} catch (Exception e) {
			log.error("No se logro crear el registro", e);
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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

}
