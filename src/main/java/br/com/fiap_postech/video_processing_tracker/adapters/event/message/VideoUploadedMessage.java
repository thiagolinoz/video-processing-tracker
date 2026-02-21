package br.com.fiap_postech.video_processing_tracker.adapters.event.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record VideoUploadedMessage(
        String nmPessoaEmail,
        String idVideoSend,
        String cdVideoStatus,
        String nmVideo,
        String nmVideoPathOrigin,
        String nmVideoPathZip,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Date dateTimeVideoCreated,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Date dateTimeVideoProcessCompleted,
        String nmPersonName
) {
}
