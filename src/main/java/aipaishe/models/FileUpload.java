package aipaishe.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by hillmon on 7/6/2017.
 */
@Entity
@Table(name = "fileupload")
public class FileUpload {

    public FileUpload() {
        // Default Constructor
    }

    public FileUpload(long eventId, String fileName, String mimeType, String srcPath) {
        this.eventId = eventId;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.srcPath = srcPath;
    }

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
}