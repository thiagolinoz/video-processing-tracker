package br.com.fiap_postech.video_processing_tracker.domain.models;

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
public class VideoModel {
    private String nmPessoaEmail;
    private String idVideoSend;
    private String cdVideoStatus;
    private String nmVideo;
    private String nmVideoPathOrigin;
    private String nmVideoPathZip;
    private Date dateTimeVideoCreated;
    private Date dateTimeVideoProcessCompleted = null;
    private String nmPersonName;

    public String getNmPessoaEmail() {
        return nmPessoaEmail;
    }

    public void setNmPessoaEmail(String nmPessoaEmail) {
        this.nmPessoaEmail = nmPessoaEmail;
    }

    public String getIdVideoSend() {
        return idVideoSend;
    }

    public void setIdVideoSend(String idVideoSend) {
        this.idVideoSend = idVideoSend;
    }

    public String getCdVideoStatus() {
        return cdVideoStatus;
    }

    public void setCdVideoStatus(String cdVideoStatus) {
        this.cdVideoStatus = cdVideoStatus;
    }

    public String getNmVideo() {
        return nmVideo;
    }

    public void setNmVideo(String nmVideo) {
        this.nmVideo = nmVideo;
    }

    public String getNmVideoPathOrigin() {
        return nmVideoPathOrigin;
    }

    public void setNmVideoPathOrigin(String nmVideoPathOrigin) {
        this.nmVideoPathOrigin = nmVideoPathOrigin;
    }

    public String getNmVideoPathZip() {
        return nmVideoPathZip;
    }

    public void setNmVideoPathZip(String nmVideoPathZip) {
        this.nmVideoPathZip = nmVideoPathZip;
    }

    public Date getDateTimeVideoCreated() {
        return dateTimeVideoCreated;
    }

    public void setDateTimeVideoCreated(Date dateTimeVideoCreated) {
        this.dateTimeVideoCreated = dateTimeVideoCreated;
    }

    public Date getDateTimeVideoProcessCompleted() {
        return dateTimeVideoProcessCompleted;
    }

    public void setDateTimeVideoProcessCompleted(Date dateTimeVideoProcessCompleted) {
        this.dateTimeVideoProcessCompleted = dateTimeVideoProcessCompleted;
    }

    public String getNmPersonName() {
        return nmPersonName;
    }

    public void setNmPersonName(String nmPersonName) {
        this.nmPersonName = nmPersonName;
    }
}
