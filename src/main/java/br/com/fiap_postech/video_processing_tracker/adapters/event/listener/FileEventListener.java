package br.com.fiap_postech.video_processing_tracker.adapters.event.listener;

import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.VideoMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.event.message.VideoUploadedMessage;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FileEventListener {

    private final VideoMetadataServicePort videoMetadataServicePort;
    private final ObjectMapper objectMapper  = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(FileEventListener.class.getName());

    public FileEventListener(VideoMetadataServicePort videoMetadataServicePort) {
        this.videoMetadataServicePort = videoMetadataServicePort;
    }

    @KafkaListener(
            topics = "received-videos"
    )
    public void consume(@Payload String message,
                        Acknowledgment acknowledgment) {
        System.out.println(">>> CHEGOU ALGO: " + message);
        try {
            VideoUploadedMessage videoUploadedMessage = objectMapper.readValue(message, VideoUploadedMessage.class);
            if (!videoUploadedMessage.isValidForProcessing()) {
                logger.log(Level.WARNING, "Mensagem inválida para processamento: {}", videoUploadedMessage);
                acknowledgment.acknowledge();
                return;
            }
            videoMetadataServicePort.handleVideo(VideoMapper.toVideoModel(videoUploadedMessage));

            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "Error while parsing message from topic");
        }
    }
//    @KafkaListener(topics = "received-videos")
//    public void consume(@Payload String rawMessage) { // Receba como String pura primeiro
//        try {
//            logger.log(Level.INFO,"Mensagem bruta recebida na AWS: {}", rawMessage);
//            // Tente fazer o parsing manual apenas para ver o erro real
//            VideoUploadedMessage objeto = objectMapper.readValue(rawMessage, VideoUploadedMessage.class);
//        } catch (Exception e) {
//            logger.log(Level.WARNING,"ERRO DE PARSING NA AWS: ", e);
//        }
//}

}
