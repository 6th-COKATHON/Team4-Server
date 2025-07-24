package com.example.team4.service;

import com.example.team4.global.exception.image.ImageUploadFailException;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.example.team4.global.exception.image.ImageErrorCode.IMAGE_UPLOAD_FAILED;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;
    public String uploadImage(MultipartFile input) {
        String fileName = UUID.randomUUID().toString(); // UUID를 이용해 고유한 파일 이름 생성
        String ext = input.getContentType();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(ext)
                .build();
        try {
            // GCP Storage에 파일 업로드
            storage.create(blobInfo, input.getInputStream());

            // 업로드된 파일의 URL 생성
            String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;

            return imageUrl;
        } catch (IOException e) {
            throw new ImageUploadFailException(IMAGE_UPLOAD_FAILED);
        }
    }
}
