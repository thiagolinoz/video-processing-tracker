package br.com.fiap_postech.video_processing_tracker.adapters.web.api.controller;

import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.VideoMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.event.message.VideoUploadedMessage;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TODO apagar classe. apenas teste de persistencia
@Service
@RestController
@RequestMapping("/api/v1")
public class VideoController {

    private final VideoMetadataServicePort videoMetadataServicePort;

    public VideoController(VideoMetadataServicePort videoMetadataServicePort) {
        this.videoMetadataServicePort = videoMetadataServicePort;
    }

    @PostMapping("/user/{email}/videos/create/{status}")
    public ResponseEntity<Void> createVideo(@PathVariable("email") String email, @PathVariable("status") String status) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        VideoUploadedMessage videoUploadedMessage = new VideoUploadedMessage(
                email,
                "uuid-12345-67890",
                status,
                "ferias_2026.mp4",
                "/uploads/raw/ferias_2026.mp4",
                "/uploads/processed/ferias_2026.zip",
                LocalDateTime.parse("2026-02-20 22:15:00", parser),
                LocalDateTime.parse("2026-02-20 22:20:00", parser),
                "Nome da pessoa"
        );
        videoMetadataServicePort.handleVideo(VideoMapper.toVideoModel(videoUploadedMessage));

        return ResponseEntity.created(URI.create("/api/v1/user/videos/create/status")).build();
    }
}
