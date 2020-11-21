package ues.occ.proyeccion.social.ws.app.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.google.cloud.storage.StorageOptions;

@Component
public class GoogleCloudFileUpload {

	// get service by env var GOOGLE_APPLICATION_CREDENTIALS. Json file generated in API & Services -> Service account key
		private static Storage storage = StorageOptions.getDefaultInstance().getService(); 
		
		public String upload(MultipartFile file) throws IOException {
			try {			
				BlobInfo blobInfo = storage.create(
					BlobInfo.newBuilder("[Bucket_name]", file.getOriginalFilename()).build(), //get original file name
					file.getBytes(), // the file
					BlobTargetOption.predefinedAcl(PredefinedAcl.PUBLIC_READ) // Set file permission
				);
				return blobInfo.getMediaLink(); // Return file url
			}catch(IllegalStateException e){
				throw new RuntimeException(e);
			}
	  	}	
}
