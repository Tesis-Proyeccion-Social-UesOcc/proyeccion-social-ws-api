package ues.occ.proyeccion.social.ws.app.service;

import java.io.IOException;
import java.sql.SQLException;
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

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import ues.occ.proyeccion.social.ws.app.dao.Documento;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.dto.DocumentoRequest;
import ues.occ.proyeccion.social.ws.app.repository.DocumentoRepository;

@Service
public class DocumentoServiceImpl implements DocumentoService {

	@Value("${component.bucketName.documentos.value}")
	private String bucketName;

	private static final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

	@Autowired
	private DocumentoRepository documentoRepository;

	@Autowired
	private Storage storage;

	@Override
	public ResponseEntity<ServiceResponse> findAll() {
		// TODO Auto-generated method stub
		return new ResponseEntity<ServiceResponse>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documentoRepository.findAll()),
				HttpStatus.OK);
	}

	public ResponseEntity<ServiceResponse> crearDocumento(DocumentoRequest model) {
		// TODO Auto-generated method stub
		//store(model.getFile());
		log.info(model.toString());
		LocalDateTime time = LocalDateTime.now();

		BlobId blobId = BlobId.of(bucketName, time + "_" + model.getFile().getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		String uri;

		try {

			Blob blob = storage.create(blobInfo, model.getFile().getBytes());
			uri = blob.getMediaLink();
			storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
			log.info("uri " + uri);
			
			Documento documento = documentoRepository
					.save(new Documento(model.getNombre(), model.getDescripcion(), uri, time));

			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documento), HttpStatus.OK);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("No se logro guardar el documento en el bucket", e);
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFailStorageDocumentBucket,
							ServiceResponse.messageFailStorageDocumentBucket, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal,
							ServiceResponse.messageFatal, ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
