package com.example.team4.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class GoogleCloudStorageConfig {

    @Bean
    public Storage storage() throws IOException {
        // 1. 환경변수에서 GCP 서비스 계정 JSON 전체를 가져오기
        String gcpPrivateKeyJson = System.getenv("GCP_PRIVATE_KEY");

        if (gcpPrivateKeyJson == null || gcpPrivateKeyJson.isBlank()) {
            throw new IllegalStateException("환경변수 GCP_PRIVATE_KEY가 설정되지 않았습니다.");
        }

        // 2. JSON 문자열을 InputStream으로 변환
        InputStream credentialsStream = new ByteArrayInputStream(gcpPrivateKeyJson.getBytes(StandardCharsets.UTF_8));

        // 3. GoogleCredentials 객체 생성
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        // 4. GCP Storage 객체 반환
        return StorageOptions.newBuilder()
                .setProjectId("team4-prod")
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
