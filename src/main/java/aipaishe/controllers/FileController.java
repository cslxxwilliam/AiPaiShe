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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hillmon on 7/6/2017.
 */
@CrossOrigin
@RestController
public class FileController {

    @Autowired
    FileUploadService fileUploadService;

    // Retrieve the file URLs for a passed event ID
    @RequestMapping(
            value = "/photos",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<PhotoLocation> downloadFile(@RequestParam("eventId") String eventId) {
        return fileUploadService.findByEventId(eventId);
    }

    // Retrieve the file URLs for a passed event ID and file type
    @RequestMapping(
            value = "/file/load",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<PhotoLocation> getPhotoLocation(@RequestParam("eventId") String eventId, @RequestParam("fileType") String fileType) {
        return fileUploadService.findByEventIdFileType(eventId, fileType);
    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST
    )
    public ResponseEntity uploadFile(MultipartHttpServletRequest request) {
        // retrieve the HTTP request parameters
        long eventId = Long.parseLong(request.getParameter("event_id"));
        String eventName = request.getParameter("event_name");
        String fileType = request.getParameter("file_type");

        try {
            Iterator<String> itr = request.getFileNames();

            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                String mimeType = file.getContentType();
                String filename = file.getOriginalFilename();
                byte[] bytes = file.getBytes();

                FileUpload newFile = new FileUpload(eventId, eventId + "-" + filename, mimeType, "", fileType);

                fileUploadService.uploadFile(newFile, bytes);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);

    }

    /*
    Added by Hillmon on 9 Mar 2018
    New REST API for uploading a file to GCP Storage
     */
    @RequestMapping(
            value = "/upload2cloud",
            method = RequestMethod.POST
    )
    public ResponseEntity uploadFileCloudStorage(MultipartHttpServletRequest request) {
        // retrieve the HTTP request parameters
        long eventId = Long.parseLong(request.getParameter("event_id"));
        String fileType = request.getParameter("file_type");

        List<String> fileUrlList = new ArrayList<>();

        try {
            Iterator<String> itr = request.getFileNames();

            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                String mimeType = file.getContentType();
                String filename = file.getOriginalFilename();
                byte[] bytes = file.getBytes();

                FileUpload fileUploadDbItem = new FileUpload(eventId, eventId + "-" + System.currentTimeMillis() + "-" + filename, mimeType, "", fileType);

                fileUrlList.add(fileUploadService.uploadFileCloudStorage(fileUploadDbItem, bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(fileUrlList, HttpStatus.OK);
    }
}