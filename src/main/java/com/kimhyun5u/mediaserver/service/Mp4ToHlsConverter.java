package com.kimhyun5u.mediaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class Mp4ToHlsConverter {
    public static Logger logger = LoggerFactory.getLogger(Mp4ToHlsConverter.class);

    public void convertMp4ToHls(String inputFile, String outputDir) throws Exception {
        String ffmpegCommand = String.format(
                "ffmpeg -i %s " +
                        "-profile:v baseline -level 3.0 -s 640x360 -start_number 0 -hls_time 10 -hls_list_size 0 " +
                        "-f hls %s/playlist.m3u8",
                inputFile, outputDir
        );

        Process process = Runtime.getRuntime().exec(ffmpegCommand);

        // FFmpeg 출력을 읽기 위한 BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info(line);
        }

        // 프로세스가 완료될 때까지 대기
        int exitCode = process.waitFor();
        logger.info("FFmpeg process exited with code " + exitCode);
    }
}
