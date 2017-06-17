package aipaishe.models;

import java.util.List;

/**
 * Created by williamxuxianglin on 7/6/17.
 */
public interface FileUploadRepository {
    List<PhotoLocation> findByFileName(String filename);

    void save(FileUpload doc, byte[] fileBytes);
}
