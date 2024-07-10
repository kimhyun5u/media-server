# Media Server
## about
Spring Boot로 구현한 미디어 서버입니다.

## requirements
- Java 17
- ffmpeg
- Gradle

## API
- GET /hls-player 업로드한 동영상 플레이어
- POST /convert/upload -F "file=@/path/file" mp4 to m3u8
