FROM amazoncorretto:21-al2023-jdk
RUN dnf update -y && dnf install -y shadow-utils \
    && dnf clean all
RUN useradd -ms /bin/bash video-processing-tracker
USER video-processing-tracker
COPY ./target/video-processing-tracker.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]