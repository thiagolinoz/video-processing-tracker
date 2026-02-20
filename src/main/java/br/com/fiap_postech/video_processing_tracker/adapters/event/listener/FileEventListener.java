package br.com.fiap_postech.video_processing_tracker.adapters.event.listener;

import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.CloudEventMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.VideoMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.dto.VideoUploadedCloudEvent;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FileEventListener {

    private final VideoMetadataServicePort videoMetadataServicePort;
    private final ObjectMapper objectMapper  = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(FileEventListener.class.getName());

    public FileEventListener(VideoMetadataServicePort videoMetadataServicePort) {
        this.videoMetadataServicePort = videoMetadataServicePort;
    }

    @KafkaListener(
            topics = "received-videos",
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                        @Header(KafkaHeaders.OFFSET) long offset,
                        Acknowledgment acknowledgment) {
        try {
            VideoUploadedModel videoUploadedModel = objectMapper.readValue(message, VideoUploadedModel.class);
            videoMetadataServicePort.handleVideo(VideoMapper.toVideoModel(videoUploadedModel));

            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "Error while parsing message from topic");
        }
    }
}
