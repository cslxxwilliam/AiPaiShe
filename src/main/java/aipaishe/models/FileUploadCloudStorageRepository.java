package aipaishe.models;

import aipaishe.services.repositories.FileUploadDao;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUploadCloudStorageRepository implements FileUploadRepository {

    @Autowired
    private FileUploadDao fileUploadDao;

    @Override
    public List<PhotoLocation> findByFileName(String fileName) {

        List<PhotoLocation> returnList = new ArrayList<>();

        try {
            List<FileUpload> fuList = fileUploadDao.getByFileName(fileName);

            if (fuList.size() > 0) {
                System.out.println("Found URLs: " + fuList.size());
                for (FileUpload fu : fuList) {
                    PhotoLocation location = new PhotoLocation(String.valueOf(fu.getEventId()), fu.getSrcPath());
                    returnList.add(location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public List<PhotoLocation> findByEventIdFileType(String eventId, String fileType) {

        // retrive the file  URL from database
        // TODO: check the pros/cons of retriving the file URL directly from cloud storage

        List<PhotoLocation> returnList = new ArrayList<>();

        try {
            List<FileUpload> fuList = fileUploadDao.getByEventIdFileType(Long.parseLong(eventId), fileType);

            if (fuList.size() > 0) {
                System.out.println("Found URLs: " + fuList.size());
                for (FileUpload fu : fuList) {
                    PhotoLocation location = new PhotoLocation(eventId, fu.getSrcPath());
                    returnList.add(location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }

    @Override
    public void save(FileUpload doc, byte[] fileBytes) {

        try {
            String publicUrl = saveCloudImage(doc, fileBytes);
            doc.setSrcPath(publicUrl);
            fileUploadDao.create(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String save2cloud(FileUpload doc, byte[] fileBytes) {
        String publicUrl = "";
        try {
            publicUrl = saveCloudImage(doc, fileBytes);
            doc.setSrcPath(publicUrl);
            fileUploadDao.create(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicUrl;
    }

    private String saveCloudImage(FileUpload doc, byte[] fileBytes) {

        String mediaLink = null;

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        Bucket targetBucket = null;

        Page<Bucket> buckets = storage.list(Storage.BucketListOption.pageSize(100),
                Storage.BucketListOption.prefix("aipaishe"));
        for (Bucket bucket : buckets.iterateAll()) {
            // do something with the bucket
            System.out.println("aipaishe bucket found!");
            targetBucket = bucket;
        }

        // File file = new File("/Users/hillmon/desktop/test.png");
        ByteArrayInputStream bais = null;

        try {
            bais = new ByteArrayInputStream(fileBytes);

            /*
            System.out.println("Total file size to read (in bytes) : "
                    + fis.available());

            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                System.out.print((char) content);
            }
            */

            Blob blob = targetBucket.create(doc.getFileName(), bais, doc.getMimeType());

            BlobId blobId = BlobId.of("aipaishe", doc.getFileName());

            // set ACL to make the data public
            Acl acl = storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            System.out.println("ACL set to: " + acl.getRole());

            mediaLink = blob.getMediaLink();
            System.out.println("Upload file public media link generated: " + mediaLink);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais != null)
                    bais.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return mediaLink;
    }
}
