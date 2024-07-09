package com.kimhyun5u.hls.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HlsController {

    @GetMapping("/hls-player")
    public String hlsPlayer() {
        return "hls-player"; // This will return the hls-player.html file
    }
}
