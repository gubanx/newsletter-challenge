FROM openjdk:8-jdk-alpine
EXPOSE 9090
VOLUME /tmp
ADD maven/email-service-0.0.1.jar email.jar
RUN sh -c 'touch email.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/email.jar"]
