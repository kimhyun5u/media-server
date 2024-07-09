package com.kimhyun5u.hls.controller;

import com.kimhyun5u.hls.service.Mp4ToHlsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/convert")
public class HlsConverterController {

    @Autowired
    private Mp4ToHlsConverter converter;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${hls.output.dir}")
    private String hlsOutputDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAndConvert(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // 클래스패스 내의 리소스 디렉토리 찾기
            File resourceDir = ResourceUtils.getFile("classpath:");

            // 업로드 디렉토리 생성 (클래스패스 내부)
            Path uploadPath = Paths.get(resourceDir.getAbsolutePath(), uploadDir);
            Files.createDirectories(uploadPath);

            // 파일 저장
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // HLS 출력 디렉토리 생성 (클래스패스 내부)
            Path hlsPath = Paths.get(resourceDir.getAbsolutePath(), hlsOutputDir);
            Files.createDirectories(hlsPath);

            // MP4를 HLS로 변환
            converter.convertMp4ToHls(filePath.toString(), hlsPath.toString());

            return ResponseEntity.ok("File uploaded and conversion started successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to convert file: " + e.getMessage());
        }
    }
}