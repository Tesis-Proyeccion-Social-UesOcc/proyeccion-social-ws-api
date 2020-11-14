package ues.occ.proyeccion.social.ws.app.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ues.occ.proyeccion.social.ws.app.dao.Certificado;
import ues.occ.proyeccion.social.ws.app.dao.ServiceResponse;
import ues.occ.proyeccion.social.ws.app.repository.CertificadoRepository;

@Service
public class CertificadoServiceImpl implements CertificadoService {
 
private static final Logger log = LoggerFactory.getLogger(CertificadoServiceImpl.class);

	@Autowired
	private CertificadoRepository certificadoRepository;
	
	@Override
	public ResponseEntity<ServiceResponse> crearCertificado(Certificado certificado) {
		log.info("certificado "+certificado.toString());
		try {
			certificado.setFechaExpedicion(LocalDateTime.now());
			log.info("Se creo un certificado "+certificado.toString());
			
			Certificado result = certificadoRepository.save(certificado);
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeOk, ServiceResponse.messageOk,result), 
					HttpStatus.CREATED);
			
		} catch (Exception e) {
			log.error("No se logro crear el registro",e);
			return new ResponseEntity<ServiceResponse>(
					new ServiceResponse(ServiceResponse.codeFatal, ServiceResponse.messageFatal,e.getMessage()), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
