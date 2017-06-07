package aipaishe.models;

/**
 * Created by williamxuxianglin on 7/6/17.
 */
public interface FileUploadRepository {
    FileUpload findByFileName(String filename);

    void save(FileUpload doc);
}
