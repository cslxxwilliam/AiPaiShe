package aipaishe.controllers;

import aipaishe.FileUploadService;
import aipaishe.models.FileUpload;
import aipaishe.models.PhotoLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hillmon on 7/6/2017.
 */
@CrossOrigin
@RestController
public class FileController {

    @Autowired
    FileUploadService fileUploadService;

    // Download a file
    @RequestMapping(
            value = "/photos",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<PhotoLocation> downloadFile(@RequestParam("eventId") String eventId) {
        return fileUploadService.findByEventId(eventId);
    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST
    )
    public ResponseEntity uploadFile(MultipartHttpServletRequest request) {
        /*
        Map<String, String[]> map = request.getParameterMap();
        //Reading the Map
        //Works for GET && POST Method
        for(String paramName:map.keySet()) {
            String[] paramValues = map.get(paramName);

            //Get Values of Param Name
            for(String valueOfParam:paramValues) {
                //Output the Values
                System.out.println("Value of Param with Name "+paramName+": "+valueOfParam);
            }
        }
        */

        // retrieve the HTTP request parameters
        String eventId = request.getParameter("event_id");
        String eventName = request.getParameter("event_name");

        if (!eventId.isEmpty()) {
            try {
                Iterator<String> itr = request.getFileNames();

                while (itr.hasNext()) {
                    String uploadedFile = itr.next();
                    MultipartFile file = request.getFile(uploadedFile);
                    String mimeType = file.getContentType();
                    String filename = file.getOriginalFilename();
                    byte[] bytes = file.getBytes();

                    FileUpload newFile = new FileUpload(eventId + "-" + filename, bytes, mimeType);

                    fileUploadService.uploadFile(newFile);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("{}", HttpStatus.OK);

        } else {

            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);

        }
    }
}