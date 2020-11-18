package ues.occ.proyeccion.social.ws.app.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private static final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

	@Value("${component.projectId.value}")
	private String projectId;

	@Value("${component.bucketName.value}")
	private String bucketName;

	@Override
	public String savePictureOnBucket(String base64Picture, String email) {
		try {
			if (base64Picture == null) {
				return null;
			}
			byte[] bites = org.apache.commons.codec.binary.Base64
					.decodeBase64((base64Picture.substring(base64Picture.indexOf(",") + 1)).getBytes());
			String format = base64Picture.trim().split(",")[0];
			format = format.split(";")[0];
			format = format.split(":")[1];
			InputStream inputStream = new ByteArrayInputStream(bites);

			return null;
			// return pathPhoto.replaceAll(" ", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void uploadObject(String objectName, String filePath) throws IOException {
		// The ID of your GCP project
		// String projectId = "your-project-id";

		// The ID of your GCS bucket
		// String bucketName = "your-unique-bucket-name";

		// The ID of your GCS object
		// String objectName = "your-object-name";

		// The path to your file to upload
		// String filePath = "path/to/your/file"

		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		// storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
		System.out.println(System.getProperty("user.dir"));
		storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

		log.info("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
	}

}
