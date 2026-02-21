package br.com.fiap_postech.video_processing_tracker.adapters.event.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoUploadedMessage {
    private String nmPersonEmail;
    private String idVideoSend;
    private String cdVideoStatus;
    private String nmVideo;
    private String nmPersonName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date dateTimeVideoProcessCompleted = null;
    private String nmVideoPathZip;
    private String errorMessage = null;

    public boolean isValidForProcessing() {
        return nmPersonEmail != null && idVideoSend != null;
    }
}
