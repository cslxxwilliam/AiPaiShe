package aipaishe.models;

import aipaishe.services.repositories.FileUploadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by williamxuxianglin on 7/6/17.
 */
@Component
public class FileUploadFileBasedRepository implements FileUploadRepository{
    public static final int BEGIN_INDEX = 8;
    @Autowired
    private Environment env;

    @Override
    public List<PhotoLocation> findByFileName(String eventId) {
        String folder = env.getProperty("file.save.path");
        File dir = new File(folder);
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(eventId+"-");
            }
        });

        assert files != null;

        // TODO: william please help to update this part to return the correct file path after folder refactor
        return Arrays.stream(files).map((f) -> new PhotoLocation(eventId, getFullPathForExternal(folder, f.getName()))).collect(toList());
    }

    private String getFullPathForExternal(String folderName, String fileName) {
        //remove ./public since web url does not need it
        return folderName.concat(fileName).substring(BEGIN_INDEX);
    }

    @Override
    public void save(FileUpload doc, byte[] fileBytes) {
        String fileSavePath = env.getProperty("file.save.path");
        String pathName = fileSavePath + doc.getFileName();
        File newFile = new File(pathName);

        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

            saveImage(doc, fileBytes, newFile);

            doc.setSrcPath(pathName);
            fileUploadDao.create(doc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveImage(FileUpload doc, byte[] fileBytes, File newFile) throws IOException {
        // InputStream in = new ByteArrayInputStream(doc.getFile());
        InputStream in = new ByteArrayInputStream(fileBytes);
        BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "jpg", newFile);
    }

    @Autowired
    private FileUploadDao fileUploadDao;

}
