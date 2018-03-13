package aipaishe;

import aipaishe.models.FileUpload;
import aipaishe.models.FileUploadCloudStorageRepository;
import aipaishe.models.FileUploadFileBasedRepository;
import aipaishe.models.PhotoLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hillmon on 7/6/2017.
 */
@Service
public class FileUploadService {

    // @Autowired
    // FileUploadRepository fileUploadRepository;

    @Autowired
    FileUploadFileBasedRepository fileUploadFileBasedRepository;

    @Autowired
    FileUploadCloudStorageRepository fileUploadCloudStorageRepository;

    // Retrieve file locations from project file system by event ID
    public List<PhotoLocation> findByEventId(String eventId) {
        // return fileUploadFileBasedRepository.findByFileName(eventId);
        return fileUploadCloudStorageRepository.findByEventIdFileType(eventId, "");
    }

    // Retrieve file locations by file system by event ID and file type
    public List<PhotoLocation> findByEventIdFileType(String eventId, String fileType) {
        return fileUploadCloudStorageRepository.findByEventIdFileType(eventId, fileType);
    }

    // Upload the file to project file system
    public void uploadFile(FileUpload doc, byte[] fileBytes) {
        fileUploadFileBasedRepository.save(doc, fileBytes);
    }

    // Upload the file to Cloud Storage (GCP), return the public URL for successful upload
    public String uploadFileCloudStorage(FileUpload doc, byte[] fileBytes) {
        return fileUploadCloudStorageRepository.save2cloud(doc, fileBytes);
    }
}