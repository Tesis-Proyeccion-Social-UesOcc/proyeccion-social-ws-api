package ues.occ.proyeccion.social.ws.app.service;

import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.mappers.CertificadoMapper;
import ues.occ.proyeccion.social.ws.app.model.CertificadoCreationDTO;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;
import ues.occ.proyeccion.social.ws.app.repository.ProyectoEstudianteRepository;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;
import ues.occ.proyeccion.social.ws.app.utils.PageableResource;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(rollbackOn = Exception.class)
public class CertificadoServiceImpl extends PageableResource<Certificado, CertificadoCreationDTO.CertificadoDTO>
		implements CertificadoService {

	private static final Logger log = LoggerFactory.getLogger(CertificadoServiceImpl.class);

	@Value("${component.bucketName.certificados.value}")
	private String bucketName;
	private final Storage storage;
	private final CertificadoRepository certificadoRepository;
	private final ProyectoEstudianteRepository proyectoEstudianteRepository;
	private final CertificadoMapper certificadoMapper;

	public CertificadoServiceImpl(Storage storage, CertificadoRepository certificadoRepository,
			ProyectoEstudianteRepository proyectoEstudianteRepository, CertificadoMapper certificadoMapper) {
		this.storage = storage;
		this.certificadoRepository = certificadoRepository;
		this.proyectoEstudianteRepository = proyectoEstudianteRepository;
		this.certificadoMapper = certificadoMapper;
	}

	@Override
	public Optional<CertificadoCreationDTO.CertificadoDTO> save(CertificadoCreationDTO dto) {
		var proyectoEstudiante = this.proyectoEstudianteRepository.findById(dto.getProyectoEstudianteId())
				.orElseThrow(() -> new ResourceNotFoundException("Project does not exists or is not assigned yet"));
		try {
			URL url = new URL(dto.getUri());
			Certificado certificado = new Certificado(proyectoEstudiante.getId(), url.toString(), LocalDateTime.now(),
					proyectoEstudiante);
			Certificado result = this.certificadoRepository.save(certificado);
			return Optional.of(this.certificadoMapper.certificadoToCertificadoDTO(result));
		} catch (MalformedURLException e) {
			log.warn(e.getMessage());
			throw new IllegalArgumentException("There was a mistake in the url format");
		} catch (Exception e) {
			log.warn(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<CertificadoCreationDTO.CertificadoDTO> uploadCertificate(int proyectoEstudianteId, MultipartFile file) {
		var proyectoEstudiante = this.proyectoEstudianteRepository.findById(proyectoEstudianteId)
				.orElseThrow(() -> new ResourceNotFoundException("Project does not exists or is not assigned yet"));

		LocalDateTime time = LocalDateTime.now();

		BlobId blobId = BlobId.of(bucketName, time + "_" + file.getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		log.info(file.getOriginalFilename());
		String uri;

		try {

			Blob blob = storage.create(blobInfo, file.getBytes());
			uri = blob.getMediaLink();
			// blob.
			storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
			log.info("uri " + uri);

			Certificado certificado = new Certificado(uri, LocalDateTime.now(), proyectoEstudiante);
			certificado = this.certificadoRepository.save(certificado);
			return Optional.of(this.certificadoMapper.certificadoToCertificadoDTO(certificado));

		} catch (MalformedURLException e) {
			log.warn(e.getMessage());
			throw new IllegalArgumentException("There was a mistake in the url format");
		} catch (Exception e) {
			log.warn(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public CertificadoCreationDTO.CertificadoDTO getCertificate(String carnet, String projectName) {
		return this.certificadoRepository
				.findByProyectoEstudiante_Estudiante_CarnetIgnoreCaseAndProyectoEstudiante_Proyecto_NombreIgnoreCase(
						carnet, projectName)
				.map(this.certificadoMapper::certificadoToCertificadoDTO)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Certificado no encontrado, asegúrese de escribir el nombre completo de su proyecto"));
	}

	@Override
	public PageDtoWrapper<Certificado, CertificadoCreationDTO.CertificadoDTO> findAll(int page, int size) {
		Pageable pageable = this.getPageable(page, size);
		Page<Certificado> certificadoPage = this.certificadoRepository.findAll(pageable);
		return this.getPagedData(certificadoPage);

	}

	@Override
	public PageDtoWrapper<Certificado, CertificadoCreationDTO.CertificadoDTO> findAllByCarnet(int page, int size,
			String carnet) {
		Pageable pageable = this.getPageable(page, size);
		Page<Certificado> certificadoPage = this.certificadoRepository
				.findAllByProyectoEstudiante_Estudiante_Carnet(carnet, pageable);
		return this.getPagedData(certificadoPage);
	}

	@Override
	protected Function<Certificado, CertificadoCreationDTO.CertificadoDTO> getMapperFunction() {
		return this.certificadoMapper::certificadoToCertificadoDTO;
	}

	@Override
	public ResponseEntity<ServiceResponse> crearCertificado(int id, MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			// validate.validator.validate(certificado);
			Certificado certificado = new Certificado();
			store(file);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			/*
			 * String carnet = certificadoRepository.findCarnet(id); if(carnet == null) {
			 * return new ResponseEntity<ServiceResponse>( new
			 * ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,
			 * "Registro de Proyecto no encontrado, no se guardo el certificado"),
			 * HttpStatus.CREATED); }
			 */
			BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename() + "-" + LocalDateTime.now());
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			String url;
			try {

				url = storage.create(blobInfo, file.getBytes()).getMediaLink();
				storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
				log.info("uri " + url);

			} catch (IOException e) {
				e.printStackTrace();
				log.error("No se logro guardar el documento en el bucket", e);
				return new ResponseEntity<>(
						new ServiceResponse(ServiceResponse.CODE_FAIL_STORAGE_DOCUMENT_BUCKET,
								ServiceResponse.MESSAGE_FAIL_STORAGE_DOCUMENT_BUCKET, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (url != null && id > 0) {
				certificado.setId(id);
				certificado.setFechaExpedicion(LocalDateTime.now());
				log.info("Se creo un certificado " + certificado.toString());
				certificado.setUri(url);
				Certificado result = certificadoRepository.save(certificado);
				return new ResponseEntity<>(
						new ServiceResponse(ServiceResponse.CODE_OK, ServiceResponse.MESSAGE_OK, result),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new ServiceResponse(ServiceResponse.CODE_OK, ServiceResponse.MESSAGE_OK,
						"Registro no creado por falta de la creacion de la URI"), HttpStatus.CREATED);

			}

		} catch (Exception e) {
			log.error("No se logro crear el registro", e);
			return new ResponseEntity<>(
					new ServiceResponse(ServiceResponse.CODE_FATAL, ServiceResponse.MESSAGE_FATAL, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public void store(MultipartFile file) {
		// TODO Auto-generated method stub

	}

}
