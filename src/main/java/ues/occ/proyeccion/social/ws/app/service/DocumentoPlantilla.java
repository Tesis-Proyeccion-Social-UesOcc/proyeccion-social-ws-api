package ues.occ.proyeccion.social.ws.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import javassist.NotFoundException;
import ues.occ.proyeccion.social.ws.app.dao.Plantilla;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.dto.DocumentoRequest;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.repository.PlantillaRepository;

@Service
public class DocumentoPlantilla implements DocumentoService {

	@Value("${component.bucketName.documentos.value}")
	private String bucketName;

	private static final Logger log = LoggerFactory.getLogger(DocumentoPlantilla.class);

	private final PlantillaRepository plantillaRepository;
	private final Storage storage;

	public DocumentoPlantilla(PlantillaRepository plantillaRepository, Storage storage) {
		this.plantillaRepository = plantillaRepository;
		this.storage = storage;
	}

	@Override
	public ResponseEntity<ServiceResponse> findAll() {
		return new ResponseEntity<>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, plantillaRepository.findAll()),
				HttpStatus.OK);
	}

	public ResponseEntity<ServiceResponse> crearDocumento(DocumentoRequest model) {
		log.info("Plantilla Request: "+model.toString());
		LocalDateTime time = LocalDateTime.now();

		BlobId blobId = BlobId.of(bucketName, time + "_" + model.getFile().getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		log.info(model.getFile().getOriginalFilename());
		String uri;

		try {

			Blob blob = storage.create(blobInfo, model.getFile().getBytes());
			uri = blob.getMediaLink();
			//blob.
			storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
			log.info("uri " + uri);

			var plantilla = plantillaRepository.save(new Plantilla(model.getNombre(), uri, time));

			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, plantilla), HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			log.error("No se logro guardar el documento en el bucket", e);
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeFailStorageDocumentBucket,
							ServiceResponse.messageFailStorageDocumentBucket, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			log.error("Error al subir un archivo",ex);
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ServiceResponse> findById(int id) {
		Optional<Plantilla> documento = Optional.of(
				plantillaRepository.findById(id).orElseThrow(() -> new RuntimeException("Documento no encontrado")));
		return new ResponseEntity<>(
				new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documento), HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ServiceResponse> findDocumentoByNombre(String nombre) {
		try {
			List<Plantilla> documentos = plantillaRepository.findByNombreContainingIgnoreCaseOrderByFechaDocumento(nombre);
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk, documentos), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	public ResponseEntity<ServiceResponse> deleteById(int id) {
		try {
			plantillaRepository.deleteById(id);

			return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk,
					ServiceResponse.messageOk, "documento eliminado con id " + id), HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@Override
	public ResponseEntity<ServiceResponse> updateTemplate(Integer id, DocumentoRequest model) {
		Plantilla plantilla = plantillaRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("No se encontro la plantilla"));
		
		LocalDateTime time = LocalDateTime.now();

		BlobId blobId = BlobId.of(bucketName, time + "_" + model.getFile().getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		log.info(model.getFile().getOriginalFilename());
		String uri;

		
		Blob blob;
		try {
			blob = storage.create(blobInfo, model.getFile().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResourceNotFoundException("Problemas al subir la plantilla: "+e.getMessage());
		}
		uri = blob.getMediaLink();
		//blob.
		storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		log.info("uri " + uri);
		plantilla.setUrl(uri);
		plantilla.setFechaDocumento(time);
		return new ResponseEntity<ServiceResponse>(new ServiceResponse(ServiceResponse.codeOk,
				ServiceResponse.messageOk, plantillaRepository.save(plantilla)), HttpStatus.OK);

	}

}
