package aipaishe.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by hillmon on 7/6/2017.
 */
@Entity
@Table(name = "fileupload")
public class FileUpload {

    public FileUpload(String fileName, byte[] file, String mimeType) {

        this.file = file;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public FileUpload() {
        // Default Constructor
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    @NotNull
    private String fileName;

    @Lob
    private byte[] file;

    @NotNull
    private String mimeType;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}