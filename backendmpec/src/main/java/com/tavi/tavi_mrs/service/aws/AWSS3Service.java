package com.tavi.tavi_mrs.service.aws;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AWSS3Service {

    String uploadFile(MultipartFile multipartFile);

    String uploadFileExcel(File file);

    Boolean deleteFileFromS3Bucket(String fileUrl);
}
