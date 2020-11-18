package ues.occ.proyeccion.social.ws.app.service;

import java.io.IOException;

public interface FileStorageService {
	
	public abstract String savePictureOnBucket(String base64Picture, String email);
	
	public static String base64Value = "data:image";

	void uploadObject(String objectName, String filePath) throws IOException;
}
