package aipaishe;

import aipaishe.models.FileUpload;
import aipaishe.models.FileUploadRepository;
import aipaishe.models.PhotoLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hillmon on 7/6/2017.
 */
@Service
public class FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    // Retrieve file
    public List<PhotoLocation> findByEventId(String eventId) {
        return fileUploadRepository.findByFileName(eventId);
    }

    // Upload the file
    public void uploadFile(FileUpload doc, byte[] fileBytes) {
        fileUploadRepository.save(doc,fileBytes);
    }
}