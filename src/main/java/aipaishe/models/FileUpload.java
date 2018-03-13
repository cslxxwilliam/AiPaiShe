package aipaishe.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Edited by hillmon on 9/3/2018.
 */
@Entity
@Table(name = "fileupload")
public class FileUpload {

    public FileUpload() {
        // Default Constructor
    }

    private String fileType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    @NotNull
    private long eventId;

    @NotNull
    private String fileName;

    @NotNull
    private String mimeType;

    @NotNull
    private String srcPath;

    public FileUpload(long eventId, String fileName, String mimeType, String srcPath, String fileType) {
        this.eventId = eventId;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.srcPath = srcPath;
        this.fileType = fileType;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}