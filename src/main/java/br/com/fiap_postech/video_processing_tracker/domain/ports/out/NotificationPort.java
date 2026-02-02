package br.com.fiap_postech.video_processing_tracker.domain.ports.out;

public interface NotificationPort {
    void send(String address);
}
