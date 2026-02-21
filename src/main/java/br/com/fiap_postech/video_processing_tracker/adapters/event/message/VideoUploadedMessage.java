package br.com.fiap_postech.video_processing_tracker.adapters.event.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record VideoUploadedMessage(
        String nmPessoaEmail,
        String idVideoSend,
        String cdVideoStatus,
        String nmVideo,
        String nmVideoPathOrigin,
        String nmVideoPathZip,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTimeVideoCreated,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTimeVideoProcessCompleted,
        String nmPersonName
) {
}
