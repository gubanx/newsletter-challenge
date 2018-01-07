FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD maven/event-service-0.0.1.jar event.jar
RUN sh -c 'touch event.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/event.jar"]
