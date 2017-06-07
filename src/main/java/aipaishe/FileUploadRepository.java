package aipaishe;

import aipaishe.models.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hillmon on 7/6/2017.
 */
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    FileUpload findByFileName(String filename);
}