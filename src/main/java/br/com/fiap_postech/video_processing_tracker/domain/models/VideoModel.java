package br.com.fiap_postech.video_processing_tracker.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoModel {
    private String nmPessoaEmail;
    private String idVideoSend;
    private String cdVideoStatus;
    private String nmVideo;
    private String nmPersonName;
    private Date dateTimeVideoProcessCompleted = null;
    private String nmVideoPathZip;
    private String errorMessage = null;

}
