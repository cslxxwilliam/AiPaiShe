package aipaishe.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by williamxuxianglin on 7/6/17.
 */
@Component
public class FileUploadFileBasedRepository implements FileUploadRepository{
    @Autowired
    private Environment env;

    @Override
    public List<PhotoLocation> findByFileName(String eventId) {
        File dir = new File(env.getProperty("file.save.path"));
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(eventId+"-");
            }
        });

        assert files != null;
        return Arrays.stream(files).map((f) -> new PhotoLocation(eventId, f.getName())).collect(toList());
    }

    @Override
    public void save(FileUpload doc) {
        String fileSavePath = env.getProperty("file.save.path");
        File newFile = new File(fileSavePath + doc.getFileName());

        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

            saveImage(doc, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImage(FileUpload doc, File newFile) throws IOException {
        InputStream in = new ByteArrayInputStream(doc.getFile());
        BufferedImage bImageFromConvert = ImageIO.read(in);

        ImageIO.write(bImageFromConvert, "jpg", newFile);
    }
}
