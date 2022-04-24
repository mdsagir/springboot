#FROM adoptopenjdk/openjdk11:latest
#WORKDIR workspace
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} spring-boot.jar
#ENTRYPOINT ["java", "-jar", "spring-boot.jar"]

FROM eclipse-temurin:11-jre as builder
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} spring-boot.jar
RUN java -Djarmode=layertools -jar spring-boot.jar extract

FROM eclipse-temurin:11-jre
RUN useradd spring
USER spring
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
