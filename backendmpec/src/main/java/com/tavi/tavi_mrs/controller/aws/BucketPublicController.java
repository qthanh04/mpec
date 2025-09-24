package com.tavi.tavi_mrs.controller.aws;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "api/s3/public")
public class BucketPublicController {

    @Autowired
    private AWSS3Service service;

    @PostMapping(value = "/upload")
    public ResponseEntity<JsonResult> uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
        String name = service.uploadFile(multipartFile);
        if (name.equals("") == false) {
//            String fileName = "https://mpec.s3.us-east-2.amazonaws.com/" + name;
              String fileName = "Users/vuquangthanh/Download" + name;
            return new ResponseEntity<JsonResult>(JsonResult.build("uploaded", fileName), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<JsonResult> deleteFile(@RequestParam(value = "url") String fileUrl) {
        Boolean bool = service.deleteFileFromS3Bucket(fileUrl);
        if (bool) {
            return JsonResult.deleted();
        }
        return JsonResult.saveError("delete-file-error");
    }
}