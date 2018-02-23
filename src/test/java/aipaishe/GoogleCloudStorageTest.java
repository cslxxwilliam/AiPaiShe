package aipaishe;

// Imports the Google Cloud client library

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GoogleCloudStorageTest {
    public static void main(String... args) {
        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // The name for the new bucket
        // String bucketName = "my-new-bucket";  // "my-new-bucket";

        // Creates the new bucket
        // Bucket bucket = storage.create(BucketInfo.of(bucketName));

        GoogleCloudStorageSnippets snippets = new GoogleCloudStorageSnippets(storage);

        // Bucket bucket = snippets.createBucket("hillmon-new-bucket");

        // System.out.printf("Bucket %s created.%n", bucket.getName());

        Bucket targetBucket = null;

        Page<Bucket> buckets = storage.list(Storage.BucketListOption.pageSize(100),
                Storage.BucketListOption.prefix("aipaishe"));
        for (Bucket bucket : buckets.iterateAll()) {
            // do something with the bucket
            System.out.println("One bucket found!");
            targetBucket = bucket;
        }

        File file = new File("/Users/hillmon/desktop/test.png");
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            /*
            System.out.println("Total file size to read (in bytes) : "
                    + fis.available());

            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                System.out.print((char) content);
            }
            */

            Blob blob = targetBucket.create("test_blob", fis, "image/png");

            System.out.println("Blob media link: " + blob.getMediaLink());

            BlobId blobId = BlobId.of("aipaishe", "test_blob");

            // set ACL to make the data public

            Acl acl = storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            System.out.println("ACL set to: " + acl.getRole());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}