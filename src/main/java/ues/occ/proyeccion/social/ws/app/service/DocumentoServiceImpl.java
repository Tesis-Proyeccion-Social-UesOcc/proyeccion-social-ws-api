package ues.occ.proyeccion.social.ws.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	private final DocumentoRepository documentoRepository;
	private final  Storage storage;

	public DocumentoServiceImpl(DocumentoRepository documentoRepository, Storage storage) {
		this.documentoRepository = documentoRepository;
		this.storage = storage;
	}

	@Override
	public ResponseEntity<ServiceResponse> findAll() {
		return new ResponseEntity<ServiceResponse>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documentoRepository.findAll()),
				HttpStatus.OK);
	}

	public ResponseEntity<ServiceResponse> crearDocumento(DocumentoRequest model) {
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
					.save(new Documento(model.getNombre(), model.getDescripcion()));

			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documento), HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			log.error("No se logro guardar el documento en el bucket", e);
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFailStorageDocumentBucket,
							ServiceResponse.messageFailStorageDocumentBucket, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ServiceResponse> findById(int id) {
		Optional<Documento> documento = Optional.of(
				documentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Documento no encontrado")));
		return new ResponseEntity<ServiceResponse>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documento), HttpStatus.OK);

	}

	public ResponseEntity<ServiceResponse> findDocumentoByNombre(String nombre) {
		try {
			List<Documento> documentos = documentoRepository.findByNombreContainingIgnoreCaseOrderByFechaDocumento(nombre);
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documentos), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ServiceResponse> deleteById(int id) {
		// TODO Auto-generated method stub
		try {
			documentoRepository.deleteById(id);

			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk,
					ServiceResponse.messageOk, "documento eliminado con id " + id), HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
