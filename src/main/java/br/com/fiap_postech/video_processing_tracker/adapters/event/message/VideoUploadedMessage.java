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
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Date dateTimeVideoCreated,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Date dateTimeVideoProcessCompleted,
        String nmPersonName
) {
}
