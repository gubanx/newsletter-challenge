FROM openjdk:8-jdk-alpine
EXPOSE 8080 8081 8443
VOLUME /tmp
ADD maven/subscription-service-0.0.1.jar subscripton.jar
RUN sh -c 'touch subscripton.jar'
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongodb/subscriptions","-Djava.security.egd=file:/dev/./urandom","-jar","/subscripton.jar"]
