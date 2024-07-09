package com.kimhyun5u.mediaserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/hls")
public class HlsRestController {

    @Value("${hls.output.dir}")
    private String hlsOutputDir;

    @GetMapping("/stream/{filename}")
    public ResponseEntity<Resource> getHlsStream(@PathVariable String filename) {
        Resource resource = new ClassPathResource(hlsOutputDir + "/" + filename);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", getContentType(filename))
                .body(resource);
    }

    private String getContentType(String filename) {
        if (filename.endsWith(".m3u8")) return "application/x-mpegURL";
        else if (filename.endsWith(".ts")) return "video/MP2T";
        else return "application/octet-stream";
    }
}