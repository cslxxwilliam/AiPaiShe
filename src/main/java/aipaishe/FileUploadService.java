package aipaishe;

import aipaishe.models.FileUpload;
import aipaishe.models.FileUploadFileBasedRepository;
import aipaishe.models.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hillmon on 7/6/2017.
 */
@Service
public class FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    // Retrieve file
    public FileUpload findByFileName(String filename) {
        return fileUploadRepository.findByFileName(filename);
    }

    // Upload the file
    public void uploadFile(FileUpload doc) {
        fileUploadRepository.save(doc);
    }
}