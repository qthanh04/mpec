package com.tavi.tavi_mrs.controller.khac;

import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/admin")

public class UploadFileController {

    private final static Logger LOGGER = Logger.getLogger(UploadFileController.class.getName());

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    private String fileName;

    @PostMapping(value = "/upload-file")
    public ResponseEntity<JsonResult> uploadFile(@RequestParam("file") MultipartFile file) {
        if (multiPartFile(file)) {
            return new ResponseEntity<>(JsonResult.build("uploaded", fileName), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean multiPartFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        try {
            fileName = LocalDateTime.now().getNano() + "_" + file.getOriginalFilename();
//            File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
            File uploadedFile = new File(System.getProperty("user.dir") + UPLOAD_DIRECTORY, fileName);
            System.out.println(uploadedFile.toString());
            uploadedFile.createNewFile();
            OutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "upload-file-error : {0}", e.getMessage() + "  " + UPLOAD_DIRECTORY + fileName);
            return false;
        }
    }

    //method for uploading multiple files
    @PostMapping(value = "/upload-multi-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonResult> uploadMultiFile(@RequestParam("files") MultipartFile[] files) {
        System.out.println(files.length);
        List<String> nameImageNews = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            multiPartFile(multipartFile);
            if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
//            String rs = multipartFile.getOriginalFilename();
            nameImageNews.add(fileName);
        }
        System.out.println(nameImageNews);
        return JsonResult.uploaded(nameImageNews);
    }

}
