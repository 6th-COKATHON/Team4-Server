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
        // 1. 환경변수에서 private_key 값 가져오기
        String privateKey = System.getenv("GCP_PRIVATE_KEY");
        if (privateKey == null || privateKey.isBlank()) {
            throw new IllegalStateException("환경변수 GCP_PRIVATE_KEY가 설정되지 않았습니다.");
        }

        // 2. team4_gcs.json 읽기
        ClassPathResource resource = new ClassPathResource("team4_gcs.json");
        String json = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // 3. 빈 private_key 자리에 주입
        String modifiedJson = json.replace("\"private_key\": \"\"",
                "\"private_key\": \"" + privateKey.replace("\n", "\\n") + "\"");

        // 4. 주입된 JSON으로 GoogleCredentials 생성
        InputStream credentialsStream = new ByteArrayInputStream(modifiedJson.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        // 5. GCP Storage 객체 반환
        return StorageOptions.newBuilder()
                .setProjectId("team4-prod")
                .setCredentials(credentials)
                .build()
                .getService();
    }
}