package aipaishe.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by williamxuxianglin on 7/6/17.
 */
@Component
public class FileUploadFileBasedRepository implements FileUploadRepository{
    @Autowired
    private Environment env;

    @Override
    public FileUpload findByFileName(String filename) {
        return null;
    }

    @Override
    public void save(FileUpload doc) {
        String fileSavePath = env.getProperty("file.save.path");
        File newFile = new File(fileSavePath + doc.getFileName());

        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }


//            try(FileOutputSt`ream fos = new FileOutputStream(newFile.getName())) {
//                fos.write(doc.getFile());
//            }catch (IOException e){
//                e.printStackTrace();
//            }

            InputStream in = new ByteArrayInputStream(doc.getFile());
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, "jpg", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
