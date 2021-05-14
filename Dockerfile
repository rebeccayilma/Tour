FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
EXPOSE 8080
ADD build/libs/*.jar tour.jar
ENTRYPOINT ["java", "-jar", "tour.jar"]
